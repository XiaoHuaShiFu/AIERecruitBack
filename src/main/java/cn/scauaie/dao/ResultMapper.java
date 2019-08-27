package cn.scauaie.dao;

import cn.scauaie.model.dao.ResultDO;

import java.util.List;

public interface ResultMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResultDO record);

    int insertSelective(ResultDO record);

    ResultDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResultDO record);

    int updateByPrimaryKey(ResultDO record);

    List<ResultDO> listByFormId(Integer formId);

    List<ResultDO> listResults();
}