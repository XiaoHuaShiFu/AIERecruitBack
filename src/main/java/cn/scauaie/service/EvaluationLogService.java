package cn.scauaie.service;

import cn.scauaie.model.ao.EvaluationLogAO;
import cn.scauaie.result.Result;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-21 16:18
 */
public interface EvaluationLogService {

    Result<EvaluationLogAO> saveEvaluationLog(EvaluationLogAO evaluationLogAO);

    Result<List<EvaluationLogAO>> listEvaluationLogs(Integer interviewerId, Integer pageNum, Integer pageSize);
}
