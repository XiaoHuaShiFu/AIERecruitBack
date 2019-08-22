package cn.scauaie.manager.impl;

import cn.scauaie.dao.FormMapper;
import cn.scauaie.dao.WorkMapper;
import cn.scauaie.manager.FormManager;
import cn.scauaie.model.ao.WorkAO;
import cn.scauaie.result.ErrorCode;
import cn.scauaie.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-22 20:31
 */
@Component("formManager")
public class FormManagerImpl implements FormManager {

    private static final Logger logger = LoggerFactory.getLogger(FormMapper.class);

    @Autowired
    private WorkMapper workMapper;

    /**
     * 删除作品，通过作品编号和报名表编号
     *
     * @param workId 作品编号
     * @param formId 报名表编号
     * @return Result<WorkAO>
     */
    @Override
    public Result<WorkAO> deleteWork(Integer workId, Integer formId) {
        int count = workMapper.getCountByFormId(formId);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The work of this id does not exist.");
        }

        count = workMapper.getCountByWorkIdAndFormId(workId, formId);
        if (count < 1) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        count = workMapper.deleteByWorkIdAndFormId(workId, formId);
        if (count < 1) {
            logger.error("Delete work fail. workId: {}, formId: {}.", workId, formId);
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Delete work fail.");
        }
        return Result.success();
    }

    /**
     * 获取作品url，通过作品编号和报名表编号
     *
     * @param workId 作品编号
     * @param formId 报名表编号
     * @return 作品url
     */
    @Override
    public String getWorkUrlByWorkIdAndFormId(Integer workId, Integer formId) {
        return workMapper.getUrlByWorkIdAndFormId(workId, formId);
    }

}
