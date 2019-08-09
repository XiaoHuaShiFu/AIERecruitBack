package cn.scauaie.dao;

import cn.scauaie.model.dao.WorkDO;

public interface WorkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WorkDO record);

    int insertSelective(WorkDO record);

    WorkDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorkDO record);

    int updateByPrimaryKey(WorkDO record);
}