package cn.scauaie.dao;

import cn.scauaie.model.dao.EvaluationDO;
import cn.scauaie.model.query.EvaluationQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EvaluationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EvaluationDO record);

    int insertSelective(EvaluationDO record);

    EvaluationDO getEvaluation(Integer id);

    int updateByPrimaryKeySelective(EvaluationDO record);

    int updateByPrimaryKey(EvaluationDO record);

    /**
     * 只能查询到面试官对应部门的评价表
     *
     * @param query EvaluationQuery
     * @return List<EvaluationDO>
     */
    List<EvaluationDO> listEvaluationsByQuery(EvaluationQuery query);

    /**
     * 获取评价，通过编号并且面试官部门要在此评价报名表的两个部门里面
     *
     * @param id 评价编号
     * @param interviewerDep 面试官部门表
     * @return EvaluationDO
     */
    EvaluationDO getEvaluationByEvaluationIdAndIfInterviewerDepEqualFormFirstDepOrSecondDep(
            @Param("id") Integer id, @Param("interviewerDep") String interviewerDep);

    /**
     * 获取评价，通过编号和面试官编号
     *
     * @param evaluationId 评价编号
     * @param interviewerId 面试官编号
     * @return EvaluationDO
     */
    EvaluationDO getEvaluationByEvaluationIdAndInterviewerId(@Param("id")Integer evaluationId, @Param("iid") Integer interviewerId);
}