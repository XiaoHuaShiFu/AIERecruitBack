package cn.scauaie.service;

import cn.scauaie.model.ao.EvaluationAO;
import cn.scauaie.model.query.EvaluationQuery;
import cn.scauaie.result.Result;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
public interface EvaluationService {

    Result<EvaluationAO> saveEvaluation(EvaluationAO evaluationAO);

    Result<EvaluationAO> checkDepAndSaveEvaluationAndSendInterviewResults(
            EvaluationAO evaluationAO, Boolean onlyPassSecondDep);

    Result<EvaluationAO> getEvaluation(Integer id);

    Result<EvaluationAO> getEvaluation(Integer id, String interviewerDep);

    Result<EvaluationAO> getEvaluation(Integer evaluationId, Integer interviewerId);

    Result<List<EvaluationAO>> listEvaluations(Integer pageNum, Integer pageSize);

    Result<List<EvaluationAO>> listEvaluations(EvaluationQuery query);

}
