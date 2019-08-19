package cn.scauaie.service.impl;

import cn.scauaie.converter.FormQueryConverter;
import cn.scauaie.dao.FormMapper;
import cn.scauaie.dao.WorkMapper;
import cn.scauaie.error.ErrorCode;
import cn.scauaie.exception.ProcessingException;
import cn.scauaie.manager.WeChatMpManager;
import cn.scauaie.manager.constant.WeChatMp;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.WorkAO;
import cn.scauaie.model.dao.DepNumberDO;
import cn.scauaie.model.dao.FormDO;
import cn.scauaie.model.dao.WorkDO;
import cn.scauaie.model.query.FormQuery;
import cn.scauaie.service.FileService;
import cn.scauaie.service.FormService;
import cn.scauaie.service.WorkService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private WorkService workService;

    @Autowired
    private FileService fileService;

    @Autowired
    private FormQueryConverter queryConverter;

    /**
     * 保存Form
     * @param code String
     * @param formAO FormAO
     * @return FormVO
     */
    @Override
    public FormAO saveForm(String code, FormAO formAO) {
        String openid = weChatMpManager.getOpenid(code, WeChatMp.AIE_RECRUIT.name());
        if (openid == null) {
            throw new ProcessingException(ErrorCode.INVALID_PARAMETER, "The code is not valid.");
        }

        FormDO formDO = new FormDO();
        BeanUtils.copyProperties(formAO, formDO);
        formDO.setOpenid(openid);
        int count = formMapper.insertIfOpenidNotExist(formDO);
        //没有插入成功，由于openid冲突
        if (count < 1) {
            throw new ProcessingException(ErrorCode.OPERATION_CONFLICT,
                    "Request was denied due to conflict, the openid already exists.");
        }

        formAO.setId(formDO.getId());
        return formAO;
    }

    /**
     * 获取FormAO通过id
     *
     * @param id 报名表编号
     * @return FormAO
     */
    @Override
    public FormAO getFormById(Integer id) {
        FormDO formDO = formMapper.selectByPrimaryKey(id);
        FormAO formAO = new FormAO();
        BeanUtils.copyProperties(formDO, formAO);
        return formAO;
    }

    /**
     * 获取名字通过报名表编号
     *
     * @param id 报名表编号
     * @return 名字
     */
    public String getName(Integer id) {
        return formMapper.getName(id);
    }

    /**
     * 通过id获取数量
     *
     * @param id 报名表编号
     * @return 数量
     */
    public int getCountById(Integer id) {
        return formMapper.getCountById(id);
    }

    @Override
    public FormAO updateForm(FormAO formAO) {
        FormDO formDO = new FormDO();
        BeanUtils.copyProperties(formAO, formDO);
        int count = formMapper.updateByPrimaryKeySelective(formDO);
        if (count < 1) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Update avatar failed.");
        }
        return getFormById(formAO.getId());
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
        WorkAO workAO = new WorkAO();
        BeanUtils.copyProperties(workDO, workAO);
        return workAO;
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

        WorkAO workAO = new WorkAO();
        BeanUtils.copyProperties(workDO, workAO);
        return workAO;
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
    public FormAO getFormByCode(String code) {
        String openid = weChatMpManager.getOpenid(code, WeChatMp.AIE_RECRUIT.name());
        return getFormAOByOpenid(openid);
    }

    /**
     * 获取FormAOList通过查询参数q
     *
     * @param pageNum 页码
     * @param pageSize 页条数
     * @param q 查询参数
     * @return FormAOList
     */
    @Override
    public List<FormAO> listForms(Integer pageNum, Integer pageSize, String q) {
        FormQuery formQuery = queryConverter.convert(q);
        formQuery.setPageNum(pageNum);
        formQuery.setPageSize(pageSize);
        List<FormAO> formAOList = listFormAOs(formQuery);
        if (formAOList == null) {
            throw new ProcessingException(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }
        return formAOList;
    }

    /**
     * 查询各个部门的报名表人数
     *
     * @param includeSecondDep 是否包含第二志愿部门
     * @return 各个部门的报名表人数
     */
    @Override
    public Map<String, Integer> listDepNumbers(Boolean includeSecondDep) {
        List<DepNumberDO> firstDepNumberDOList = formMapper.listFirstDepNumbers();
        Map<String, Integer> depNumberMap = new HashMap<>(16);
        for (DepNumberDO depNumberDO : firstDepNumberDOList) {
            depNumberMap.merge(depNumberDO.getDep(), depNumberDO.getNumber(),
                    (oldNumber, newNumber) -> oldNumber + newNumber);
        }
        if (!includeSecondDep) {
            return depNumberMap;
        }

        List<DepNumberDO> secondDepNumberDOList = formMapper.listSecondDepNumbers();
        for (DepNumberDO depNumberDO : secondDepNumberDOList) {
            depNumberMap.merge(depNumberDO.getDep(), depNumberDO.getNumber(),
                    (oldNumber, newNumber) -> oldNumber + newNumber);
        }

        //减去第一第二志愿部门都一样的人数
        List<DepNumberDO> firstDepSameAsSecondDepDepNumberDOList = formMapper.listDepNumbersIfFirstDepSameAsSecondDep();
        for (DepNumberDO depNumberDO : firstDepSameAsSecondDepDepNumberDOList) {
            depNumberMap.merge(depNumberDO.getDep(), depNumberDO.getNumber(),
                    (oldNumber, newNumber) -> oldNumber - newNumber);
        }

        return depNumberMap;
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

        int count = formMapper.updateAvatar(id, newAvatarUrl);
        if (count < 1) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Update avatar failed.");
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
        FormAO formAO = new FormAO();
        BeanUtils.copyProperties(formDO, formAO);

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
     * 查询FormAOList通过FormQuery
     *
     * @param formQuery FormQuery
     * @return FormAOList
     */
    private List<FormAO> listFormAOs(FormQuery formQuery) {
        PageHelper.startPage(formQuery.getPageNum(), formQuery.getPageSize());
        List<FormDO> formDOList = formMapper.listByFormQuery(formQuery);
        if (formDOList.size() < 1) {
            return null;
        }
        List<FormAO> formAOList = new ArrayList<>(formDOList.size());
        for (FormDO formDO : formDOList) {
            List<WorkAO> workAOList = workService.listWorkAOsByFormId(formDO.getId());
            FormAO formAO = new FormAO();
            BeanUtils.copyProperties(formDO, formAO);
            formAO.setWorks(workAOList);
            formAOList.add(formAO);
        }
        return formAOList;
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
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Update work failed.");
        }

        return workDO;
    }

}
