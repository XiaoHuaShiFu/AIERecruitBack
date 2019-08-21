package cn.scauaie.service;

import cn.scauaie.model.ao.EvaluationAO;
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

    Result<EvaluationAO> checkDepAndSaveEvaluation(EvaluationAO evaluationAO);

    EvaluationAO getEvaluation(Integer id);

    List<EvaluationAO> listEvaluations(Integer pageNum, Integer pageSize, String q);
}
