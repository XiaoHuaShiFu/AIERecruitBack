package cn.scauaie.dao;

import cn.scauaie.model.ao.EvaluationLogAO;
import cn.scauaie.model.dao.EvaluationLogDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EvaluationLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EvaluationLogDO record);

    int insertSelective(EvaluationLogDO record);

    EvaluationLogDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EvaluationLogDO record);

    int updateByPrimaryKey(EvaluationLogDO record);

    int saveEvaluationLog(EvaluationLogDO evaluationLogDO);

    List<EvaluationLogDO> listEvaluationLogs(@Param("iid") Integer interviewerId);
}