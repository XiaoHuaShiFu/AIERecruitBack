package cn.scauaie.service.impl;

import cn.scauaie.dao.EvaluationLogMapper;
import cn.scauaie.model.ao.EvaluationLogAO;
import cn.scauaie.model.dao.EvaluationLogDO;
import cn.scauaie.result.ErrorCode;
import cn.scauaie.result.Result;
import cn.scauaie.service.EvaluationLogService;
import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-21 16:18
 */
@Service("evaluationLogService")
public class EvaluationLogServiceImpl implements EvaluationLogService {

    private static final Logger logger = LoggerFactory.getLogger(EvaluationLogServiceImpl.class);

    @Autowired
    private EvaluationLogMapper evaluationLogMapper;

    @Autowired
    private Mapper mapper;

    /**
     * 保存评价日志
     * 不对外开放
     * 不检验参数
     *
     * @param evaluationLogAO EvaluationLogAO
     * @return EvaluationLogAO
     */
    @Override
    public Result<EvaluationLogAO> saveEvaluationLog(EvaluationLogAO evaluationLogAO) {
        EvaluationLogDO evaluationLogDO = mapper.map(evaluationLogAO, EvaluationLogDO.class);
        int count = evaluationLogMapper.saveEvaluationLog(evaluationLogDO);
        if (count < 1) {
            logger.error("Insert evaluationLog fail. eid: {}, fid: {}, iid: {}, message: {}", evaluationLogAO.getEid(),
                    evaluationLogAO.getFid(), evaluationLogAO.getIid(), evaluationLogAO.getMessage());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert evaluationLog fail.");
        }
        evaluationLogAO.setId(evaluationLogDO.getId());
        return Result.success(evaluationLogAO);
    }

    /**
     * 获取评价日志列表
     *
     * @param interviewerId 面试官编号
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return Result<List<EvaluationLogAO>>
     */
    @Override
    public Result<List<EvaluationLogAO>> listEvaluationLogs(Integer interviewerId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<EvaluationLogDO> evaluationLogDOList = evaluationLogMapper.listEvaluationLogs(interviewerId);
        if (evaluationLogDOList.size() < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(
                evaluationLogDOList.stream()
                .map(evaluationLogDO -> mapper.map(evaluationLogDO, EvaluationLogAO.class))
                .collect(Collectors.toList()));
    }

}
