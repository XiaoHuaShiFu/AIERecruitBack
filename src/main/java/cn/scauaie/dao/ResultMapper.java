package cn.scauaie.dao;

import cn.scauaie.model.dao.ResultDO;

public interface ResultMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResultDO record);

    int insertSelective(ResultDO record);

    ResultDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResultDO record);

    int updateByPrimaryKey(ResultDO record);
}