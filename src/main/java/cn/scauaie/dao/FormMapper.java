package cn.scauaie.dao;

import cn.scauaie.model.dao.FormDO;

public interface FormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FormDO record);

    int insertSelective(FormDO record);

    FormDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FormDO record);

    int updateByPrimaryKey(FormDO record);

    FormDO selectByOpenid(String openid);
}