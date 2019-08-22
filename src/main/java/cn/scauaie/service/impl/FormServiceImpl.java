package cn.scauaie.service.impl;

import cn.scauaie.converter.FormQueryConverter;
import cn.scauaie.dao.FormMapper;
import cn.scauaie.manager.WeChatMpManager;
import cn.scauaie.manager.constant.WeChatMp;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.WorkAO;
import cn.scauaie.model.dao.DepNumberDO;
import cn.scauaie.model.dao.FormDO;
import cn.scauaie.model.query.FormQuery;
import cn.scauaie.result.ErrorCode;
import cn.scauaie.result.Result;
import cn.scauaie.service.FileService;
import cn.scauaie.service.FormService;
import cn.scauaie.service.WorkService;
import cn.scauaie.util.BeanCheckerUtils;
import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(FormServiceImpl.class);

    @Autowired
    private FormMapper formMapper;

    @Autowired
    private WorkService workService;

    @Autowired
    private FileService fileService;

    @Autowired
    private WeChatMpManager weChatMpManager;

    @Autowired
    private Mapper mapper;

    @Autowired
    private FormQueryConverter queryConverter;

    /**
     * 头像文件目录前缀
     */
    private static final String PREFIX_AVATAR_FILE_DIRECTORY = "/recruit/form/avatar/";

    /**
     * 保存Form
     * @param code String
     * @param formAO FormAO
     * @return Result<FormAO>
     */
    @Override
    public Result<FormAO> saveForm(String code, FormAO formAO) {
        String openid = weChatMpManager.getOpenid(code, WeChatMp.AIE_RECRUIT.name());
        //获取openid失败
        if (openid == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "The code is not valid.");
        }

        //openid已经在数据库里
        int count = formMapper.getCountByOpenid(openid);
        if (count >= 1) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT,
                    "Request was denied due to conflict, the openid already exists.");
        }

        FormDO formDO = mapper.map(formAO, FormDO.class);
        formDO.setOpenid(openid);
        count = formMapper.insertIfOpenidNotExist(formDO);
        //没有插入成功
        if (count < 1) {
            logger.error("Insert form fail.");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert form fail.");
        }

        formAO.setId(formDO.getId());
        return Result.success(formAO);
    }

    /**
     * 获取FormAO通过id
     *
     * @param id 报名表编号
     * @return FormAO
     */
    @Override
    public FormAO getForm(Integer id) {
        FormDO formDO = formMapper.getForm(id);
        if (formDO == null) {
            return null;
        }
        List<WorkAO> workAOList = workService.listWorksByFormId(id);

        FormAO formAO = mapper.map(formDO, FormAO.class);
        formAO.setWorks(workAOList);
        return formAO;
    }

    /**
     * 获取FormAO通过code
     *
     * @param code wx.login()接口的返回值
     * @return Result<FormAO>
     */
    @Override
    public Result<FormAO> getFormByCode(String code) {
        String openid = weChatMpManager.getOpenid(code, WeChatMp.AIE_RECRUIT.name());

        FormDO formDO = formMapper.getFormByOpenid(openid);
        //通过openid获取失败
        if (formDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified openid does not exist.");
        }

        FormAO formAO = mapper.map(formDO, FormAO.class);
        formAO.setWorks(workService.listWorksByFormId(formDO.getId()));

        return Result.success(formAO);
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
    public Result<List<FormAO>> listForms(Integer pageNum, Integer pageSize, String q) {
        FormQuery formQuery = queryConverter.convert(q);
        formQuery.setPageNum(pageNum);
        formQuery.setPageSize(pageSize);
        List<FormAO> formAOList = listForms(formQuery);
        if (formAOList == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }
        return Result.success(formAOList);
    }

    /**
     * 更新报名表
     *
     * @param formAO 要更新的信息
     * @return 更新后的报名表信息
     */
    @Override
    public Result<FormAO> updateForm(FormAO formAO) {
        //只给更新某些属性
        FormDO formDO = new FormDO();
        formDO.setName(formAO.getName());
        formDO.setGender(formAO.getGender());
        formDO.setCollege(formAO.getCollege());
        formDO.setMajor(formAO.getMajor());
        formDO.setPhone(formAO.getPhone());
        formDO.setIntroduction(formAO.getIntroduction());
        //所有参数都为空
        if (BeanCheckerUtils.allFieldIsNULL(formDO)) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_IS_BLANK,
                    "The required parameter must be not all null.");
        }

        formDO.setId(formAO.getId());
        int count = formMapper.updateByPrimaryKeySelective(formDO);
        if (count < 1) {
            logger.error("Update avatar failed. formId: {}", formAO.getId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Update avatar failed.");
        }

        return Result.success(getForm(formAO.getId()));
    }

    /**
     * 获取名字通过报名表编号
     *
     * @param id 报名表编号
     * @return 名字
     */
    @Override
    public String getName(Integer id) {
        return formMapper.getName(id);
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
     * 获取第一部门通过报名表编号
     *
     * @param id 报名表编号
     * @return 第一部门
     */
    public String getFirstDep(Integer id) {
        return formMapper.getFirstDep(id);
    }

    /**
     * 上传头像
     *
     * @param formId 报名表编号
     * @param avatar MultipartFile
     * @return 头像url
     */
    @Override
    public Result<String> saveAvatar(Integer formId, MultipartFile avatar) {
        String oldAvatarUrl = formMapper.getAvatar(formId);

        //旧头像已经存在
        if (oldAvatarUrl != null) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT, "The specified avatar already exist.");
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
    public Result<String> updateAvatar(Integer formId, MultipartFile avatar) {
        //获取旧文件Url
        String oldAvatarUrl = formMapper.getAvatar(formId);
        //更新文件
        return updateAvatarByOldAvatarUrl(formId, oldAvatarUrl, avatar);
    }

    /**
     * 上传作品
     *
     * @param formId 报名表编号
     * @param work MultipartFile
     * @return WorkVO
     */
    @Override
    public Result<WorkAO> saveWork(Integer formId, MultipartFile work) {
        return workService.saveWork(formId, work);
    }

    /**
     * 删除作品，通过作品编号和报名表编号
     *
     * @param workId 作品编号
     * @param formId 报名表编号
     * @return Result<WorkAO>
     */
    @Override
    public Result<WorkAO> deleteWork(Integer workId, Integer formId) {
        return workService.deleteWork(workId, formId);
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
    public Result<WorkAO> updateWork(Integer workId, Integer formId, MultipartFile work) {
        return workService.updateWork(workId, formId, work);
    }

    /**
     * 查询FormAOList通过FormQuery
     *
     * @param formQuery FormQuery
     * @return FormAOList
     */
    private List<FormAO> listForms(FormQuery formQuery) {
        PageHelper.startPage(formQuery.getPageNum(), formQuery.getPageSize());
        List<FormDO> formDOList = formMapper.listByFormQuery(formQuery);
        if (formDOList.size() < 1) {
            return null;
        }
        List<FormAO> formAOList = new ArrayList<>(formDOList.size());
        for (FormDO formDO : formDOList) {
            FormAO formAO = mapper.map(formDO, FormAO.class);
            List<WorkAO> workAOList = workService.listWorksByFormId(formDO.getId());
            formAO.setWorks(workAOList);
            formAOList.add(formAO);
        }
        return formAOList;
    }

    /**
     * 更新头像
     *
     * @param id 报名表编号
     * @param oldAvatarUrl String
     * @param avatar MultipartFile
     * @return 新文件url
     */
    private Result<String> updateAvatarByOldAvatarUrl(Integer id, String oldAvatarUrl, MultipartFile avatar) {
        //更新头像文件
        String newAvatarUrl = fileService.updateFile(avatar, oldAvatarUrl, PREFIX_AVATAR_FILE_DIRECTORY);

        int count = formMapper.updateAvatar(id, newAvatarUrl);
        if (count < 1) {
            logger.error("Update avatar failed. id: {}", id);
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Update avatar failed.");
        }

        return Result.success(newAvatarUrl);
    }

}
