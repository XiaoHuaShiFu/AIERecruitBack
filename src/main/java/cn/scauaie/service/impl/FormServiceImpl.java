package cn.scauaie.service.impl;

import cn.scauaie.assembler.FormAssembler;
import cn.scauaie.assembler.WorkAssembler;
import cn.scauaie.common.error.ErrorCode;
import cn.scauaie.dao.FormMapper;
import cn.scauaie.dao.WorkMapper;
import cn.scauaie.error.ProcessingException;
import cn.scauaie.manager.wechat.WeChatMpManager;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.WorkAO;
import cn.scauaie.model.dao.FormDO;
import cn.scauaie.model.dao.WorkDO;
import cn.scauaie.service.FileService;
import cn.scauaie.service.FormService;
import cn.scauaie.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
@Service("formService")
public class FormServiceImpl implements FormService {

    @Autowired
    private FormMapper formMapper;

    @Autowired
    private WorkMapper workMapper;

    @Autowired
    private WeChatMpManager weChatMpManager;

    @Autowired
    private FormAssembler formAssembler;

    @Autowired
    private WorkAssembler workAssembler;

    @Autowired
    private WorkService workService;

    @Autowired
    private FileService fileService;

    /**
     * 保存Form
     * @param code String
     * @param formAO FormAO
     * @return FormVO
     */
    @Override
    public FormAO saveForm(String code, FormAO formAO) {
        String openid = weChatMpManager.getOpenid(code);
        if (openid == null) {
            throw new ProcessingException(ErrorCode.INVALID_PARAMETER, "The code is not valid.");
        }

        FormDO formDO = formAssembler.assembleFormDOByOpenidAndFormAO(openid, formAO);
        int count = formMapper.insertIfOpenidNotExist(formDO);
        //没有插入成功，由于openid冲突
        if (count < 1) {
            throw new ProcessingException(ErrorCode.OPERATION_CONFLICT,
                    "Request was denied due to conflict, the openid already exists.");
        }
        return formAssembler.assembleFormAOByFormDO(formDO);
    }

    /**
     * 更新作品
     *
     * @param workId 作品编号
     * @param formId 报名表编号
     * @param work MultipartFile
     * @return WorkVO
     */
    @Override
    public WorkAO updateWork(Integer workId, Integer formId, MultipartFile work) {
        //查找旧作品url
        String oldWorkUrl = workMapper.selectUrlByFormIdAndWorkId(workId, formId);
        //旧作品不存在
        if (oldWorkUrl == null) {
            throw new ProcessingException(ErrorCode.FORBIDDEN_SUB_USER,
                    "The specified action is not available for you.");
        }
        //更新作品
        WorkDO workDO = updatedWorkByOldWorkUrl(workId, oldWorkUrl, work);
        return workAssembler.assembleWorkAOByWorkDO(workDO);
    }

    /**
     * 上传作品
     *
     * @param formId 报名表编号
     * @param work MultipartFile
     * @return WorkVO
     */
    @Override
    public WorkAO saveWork(Integer formId, MultipartFile work) {
        //查询作品数量
        int count = workMapper.selectCountByFormId(formId);
        //作品数量已经到达上限
        if (count >= 6) {
            throw new ProcessingException(ErrorCode.OPERATION_CONFLICT,
                    "The specified work number has reached the upper limit.");
        }

        //上传作品文件
        String workUrl = fileService.saveAndGetUrl(work, "/recruit/form/work/");
        WorkDO workDO = new WorkDO();
        workDO.setUrl(workUrl);
        workDO.setFid(formId);

        //添加作品到数据库
        count = workMapper.insertSelective(workDO);
        //插入失败
        if (count < 1) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Insert work failed.");
        }

        return workAssembler.assembleWorkAOByWorkDO(workDO);
    }

    /**
     * 上传头像
     *
     * @param formId 报名表编号
     * @param avatar MultipartFile
     * @return 头像url
     */
    @Override
    public String saveAvatar(Integer formId, MultipartFile avatar) {
        String oldAvatarUrl = formMapper.selectAvatarById(formId);

        //旧头像已经存在
        if (oldAvatarUrl != null) {
            throw new ProcessingException(ErrorCode.OPERATION_CONFLICT,
                    "The specified avatar already exist.");
        }

        //创建头像文件并添加到数据库
        return updateAvatarByOldAvatarUrl(formId, null, avatar);
    }

    /**
     * 更新头像
     *
     * @param formId FormId
     * @param avatar MultipartFile
     * @return 新文件url
     */
    @Override
    public String updateAvatar(Integer formId, MultipartFile avatar) {
        //获取旧文件Url
        String oldAvatarUrl = formMapper.selectAvatarById(formId);
        //更新文件
        return updateAvatarByOldAvatarUrl(formId, oldAvatarUrl, avatar);
    }

    /**
     * 获取FormAO通过code
     *
     * @param code wx.login()接口的返回值
     * @return FormAO
     */
    @Override
    public FormAO getFormAOByCode(String code) {
        String openid = weChatMpManager.getOpenid(code);
        return getFormAOByOpenid(openid);
    }

    /**
     * 更新头像
     *
     * @param id 报名表编号
     * @param oldAvatarUrl String
     * @param avatar MultipartFile
     * @return 新文件url
     */
    private String updateAvatarByOldAvatarUrl(Integer id, String oldAvatarUrl, MultipartFile avatar) {
        //更新头像文件
        String newAvatarUrl = fileService.updateFile(avatar, oldAvatarUrl, "/recruit/form/avatar/");

        //更改数据库里的avatar
        FormDO formDO = new FormDO();
        formDO.setId(id);
        formDO.setAvatar(newAvatarUrl);
        int count = formMapper.updateByPrimaryKeySelective(formDO);
        if (count < 1) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Update avatar error.");
        }

        return newAvatarUrl;
    }

    /**
     * 获取FormAO通过openid
     *
     * @param openid 微信用户唯一标识符
     * @return FormAO
     */
    private FormAO getFormAOByOpenid(String openid) {
        FormDO formDO = getFormDOByOpenid(openid);
        FormAO formAO = formAssembler.assembleFormAOByFormDO(formDO);
        List<WorkAO> workAOList = workService.listWorkAOsByFormId(formDO.getId());
        formAO.setWorks(workAOList);
        return formAO;
    }

    /**
     * 获取FormDO通过openid
     *
     * @param openid 微信用户唯一标识符
     * @return FormDO
     */
    private FormDO getFormDOByOpenid(String openid) {
        FormDO formDO = formMapper.selectByOpenid(openid);
        //通过openid获取失败将报错
        if (formDO == null) {
            throw new ProcessingException(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The specified openid does not exist.");
        }
        return formDO;
    }

    /**
     * 更新作品
     *
     * @param workId 作品编号
     * @param oldWorkUrl String
     * @param work MultipartFile
     * @return WorkVO
     */
    private WorkDO updatedWorkByOldWorkUrl(Integer workId, String oldWorkUrl, MultipartFile work) {
        //更新作品文件
        String newWorkUrl = fileService.updateFile(work, oldWorkUrl, "/recruit/form/work/");

        //更改数据库里的work
        WorkDO workDO = new WorkDO();
        workDO.setId(workId);
        workDO.setUrl(newWorkUrl);
        int count = workMapper.updateByPrimaryKeySelective(workDO);
        if (count < 1) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Update work error.");
        }

        return workDO;
    }

}
