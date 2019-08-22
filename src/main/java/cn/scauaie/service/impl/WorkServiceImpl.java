package cn.scauaie.service.impl;

import cn.scauaie.dao.WorkMapper;
import cn.scauaie.model.ao.WorkAO;
import cn.scauaie.model.dao.WorkDO;
import cn.scauaie.result.ErrorCode;
import cn.scauaie.result.Result;
import cn.scauaie.service.FileService;
import cn.scauaie.service.WorkService;
import com.github.dozermapper.core.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
@Service("workService")
public class WorkServiceImpl implements WorkService {

    private static final Logger logger = LoggerFactory.getLogger(WorkServiceImpl.class);

    @Autowired
    private WorkMapper workMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private Mapper mapper;

    private static final String PREFIX_WORK_FILE_DIRECTORY = "/recruit/form/work/";

    /**
     * 上传作品
     *
     * @param formId 报名表编号
     * @param work MultipartFile
     * @return WorkVO
     */
    @Override
    public Result<WorkAO> saveWork(Integer formId, MultipartFile work) {
        //查询作品数量
        int count = workMapper.getCountByFormId(formId);
        //作品数量已经到达上限
        if (count >= 6) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT, "The specified work number has reached the upper limit.");
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
            logger.error("Insert work failed. formId: {}", formId);
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert work failed.");
        }

        return Result.success(mapper.map(workDO, WorkAO.class));
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
        //先获取作品url，否则删除之后就拿不到url了
        String url = workMapper.getUrlByWorkIdAndFormId(workId, formId);
        if (url == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified resource does not exist.");
        }

        //删除作品
        int count = workMapper.deleteByWorkIdAndFormId(workId, formId);
        if (count < 1) {
            logger.error("Delete work fail. workId: {}, formId: {}.", workId, formId);
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Delete work fail.");
        }

        //删除作品文件
        fileService.delete(url);
        return Result.success();
    }

    /**
     * 获取对应报名表编号的作品 List<WorkAO>
     *
     * @param formId 报名表编号
     * @return List<WorkAO>
     */
    @Override
    public List<WorkAO> listWorksByFormId(Integer formId) {
        List<WorkDO> workDOList = workMapper.selectByFid(formId);
        System.out.println(workDOList);
        if (workDOList.size() < 1) {
            return null;
        }
        List<WorkAO> workAOList = new ArrayList<>(workDOList.size());
        mapper.map(workDOList, workAOList);
        return workAOList;
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
        String oldWorkUrl = workMapper.getUrlByFormIdAndWorkId(workId, formId);
        //旧作品不存在
        if (oldWorkUrl == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified resource does not exist.");
        }

        //更新作品
        return updatedWorkByOldWorkUrl(workId, oldWorkUrl, work);
    }

    /**
     * 更新作品
     *
     * @param workId 作品编号
     * @param oldWorkUrl String
     * @param work MultipartFile
     * @return WorkVO
     */
    @Override
    public Result<WorkAO> updatedWorkByOldWorkUrl(Integer workId, String oldWorkUrl, MultipartFile work) {
        //更新作品文件
        String newWorkUrl = fileService.updateFile(work, oldWorkUrl, PREFIX_WORK_FILE_DIRECTORY);

        //更改数据库里的work
        WorkDO workDO = new WorkDO();
        workDO.setId(workId);
        workDO.setUrl(newWorkUrl);
        int count = workMapper.updateByPrimaryKeySelective(workDO);
        if (count < 1) {
            logger.error("Update work failed. workId: {}, oldWorkUrl: {}", workId, oldWorkUrl);
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Update work failed.");
        }

        return Result.success(mapper.map(workDO, WorkAO.class));
    }

}
