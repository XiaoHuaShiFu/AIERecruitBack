package cn.scauaie.dao;

import cn.scauaie.model.dao.QueueDO;

public interface QueueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QueueDO record);

    int insertSelective(QueueDO record);

    QueueDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QueueDO record);

    int updateByPrimaryKey(QueueDO record);
}