package cn.scauaie.dao;

import cn.scauaie.model.dao.EvaluationDO;
import cn.scauaie.model.query.EvaluationQuery;

import java.util.List;

public interface EvaluationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EvaluationDO record);

    int insertSelective(EvaluationDO record);

    EvaluationDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EvaluationDO record);

    int updateByPrimaryKey(EvaluationDO record);

    List<EvaluationDO> listEvaluationsByQuery(EvaluationQuery query);
}