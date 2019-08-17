package cn.scauaie.dao;

import cn.scauaie.model.dao.QueuerDO;

public interface QueuerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QueuerDO record);

    int insertSelective(QueuerDO record);

    QueuerDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QueuerDO record);

    int updateByPrimaryKey(QueuerDO record);

    QueuerDO getByFormId(Integer formId);
}