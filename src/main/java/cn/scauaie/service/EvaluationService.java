package cn.scauaie.service;

import cn.scauaie.model.ao.EvaluationAO;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
public interface EvaluationService {

    EvaluationAO saveEvaluation(EvaluationAO evaluationAO);

    EvaluationAO getEvaluation(Integer id);

}
