package cn.scauaie.dao;

import cn.scauaie.model.dao.EvaluationDO;

public interface EvaluationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EvaluationDO record);

    int insertSelective(EvaluationDO record);

    EvaluationDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EvaluationDO record);

    int updateByPrimaryKey(EvaluationDO record);
}