package cn.scauaie.service.impl;

import cn.scauaie.assembler.FormAssembler;
import cn.scauaie.assembler.WorkAssembler;
import cn.scauaie.common.error.ErrorCode;
import cn.scauaie.dao.FormMapper;
import cn.scauaie.dao.WorkMapper;
import cn.scauaie.error.ProcessingException;
import cn.scauaie.manager.WeChatMpManager;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.dao.FormDO;
import cn.scauaie.model.dao.WorkDO;
import cn.scauaie.model.vo.AvatarVO;
import cn.scauaie.model.vo.FormVO;
import cn.scauaie.model.vo.WorkVO;
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
     * 更新作品
     *
     * @param workId 作品编号
     * @param oldWorkUrl String
     * @param work MultipartFile
     * @return WorkVO
     */
    public WorkDO updatedWorkByOldWorkUrl(Integer workId, String oldWorkUrl, MultipartFile work) {
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

    /**
     * 上传作品
     *
     * @param fid 报名表编号
     * @param work MultipartFile
     * @return WorkVO
     */
    @Override
    public WorkVO uploadWork(Integer fid, MultipartFile work) {
        //查询作品数量
        int count = workMapper.selectCountByFormId(fid);
        //作品数量已经到达上限
        if (count >= 6) {
            throw new ProcessingException(ErrorCode.OPERATION_CONFLICT,
                    "The specified work number has reached the upper limit.");
        }

        //上传作品文件
        String workUrl = fileService.uploadAndGetUrl(work, "/recruit/form/work/");
        WorkDO workDO = new WorkDO();
        workDO.setUrl(workUrl);
        workDO.setFid(fid);

        //添加作品到数据库
        count = workMapper.insertSelective(workDO);
        //插入失败
        if (count < 1) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Insert work failed.");
        }

        return workAssembler.assembleWorkVOByWorkDO(workDO);
    }

    /**
     * 上传头像
     *
     * @param id 报名表编号
     * @param avatar MultipartFile
     * @return 头像url
     */
    @Override
    public AvatarVO uploadAvatar(Integer id, MultipartFile avatar) {
        String oldAvatarUrl = formMapper.selectAvatarById(id);
        if (oldAvatarUrl != null) {
            throw new ProcessingException(ErrorCode.OPERATION_CONFLICT,
                    "The specified avatar already exist.");
        }
        String avatarUrl = updatedAvatarByOldAvatarUrl(id, null, avatar);
        return new AvatarVO(avatarUrl);
    }

    /**
     * 更新头像
     *
     * @param id FormId
     * @param avatar MultipartFile
     * @return 新文件url
     */
    public String updatedAvatar(Integer id, MultipartFile avatar) {
        //获取旧文件Url
        String oldAvatarUrl = formMapper.selectAvatarById(id);
        //更新文件
        return updatedAvatarByOldAvatarUrl(id, oldAvatarUrl, avatar);
    }

    /**
     * 更新头像
     *
     * @param id 报名表编号
     * @param oldAvatarUrl String
     * @param avatar MultipartFile
     * @return 新文件url
     */
    public String updatedAvatarByOldAvatarUrl(Integer id, String oldAvatarUrl, MultipartFile avatar) {
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
     * 获取FormVO通过openid
     *
     * @param openid 微信用户唯一标识符
     * @return FormVO
     */
    @Override
    public FormVO getFormVOByOpenid(String openid) {
        FormDO formDO = getFormDOByOpenid(openid);
        FormVO formVO = formAssembler.assembleFormVOByFormDO(formDO);
        List<WorkVO> workVOs = workService.getWorkVOsByFormId(formDO.getId());
        formVO.setWorks(workVOs);
        return formVO;
    }

    /**
     * 获取FormDO通过openid
     *
     * @param openid 微信用户唯一标识符
     * @return FormDO
     */
    @Override
    public FormDO getFormDOByOpenid(String openid) {
        FormDO formDO = formMapper.selectByOpenid(openid);
        //通过openid获取失败将报错
        if (formDO == null) {
            throw new ProcessingException(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The specified openid does not exist.");
        }
        return formDO;
    }

    /**
     * 校验code
     *
     * @param code String
     * @return String
     */
    @Override
    public String getOpenid(String code) {
        String openid = weChatMpManager.getOpenidByCode(code);
        //通过openid获取失败将报错
        if (openid == null) {
            throw new ProcessingException(ErrorCode.INVALID_PARAMETER, "The code is not valid.");
        }
        return openid;
    }

    /**
     * 直接保存Form，不进行参数校验
     * @param openid String
     * @param formAO FormAO
     * @return FormVO
     */
    @Override
    public FormVO createForm(String openid, FormAO formAO) {
        FormDO formDO = formAssembler.assembleFormDOByOpenidAndFormAO(openid, formAO);
        return formAssembler.assembleFormVOByFormDO(createForm(formDO));
    }

    /**
     * 直接保存Form，不进行参数校验
     * @param formDO FormDO
     * @return FormDO
     */
    @Override
    public FormDO createForm(FormDO formDO) {
        //没有成功创建
        if (formMapper.insertSelective(formDO) < 1) {
            throw new ProcessingException(ErrorCode.OPERATION_CONFLICT,
                    "Request was denied due to conflict, the openid already exists.");
        }
        return formDO;
    }

}
