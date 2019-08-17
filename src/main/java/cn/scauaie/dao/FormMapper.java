package cn.scauaie.dao;

import cn.scauaie.model.dao.DepNumberDO;
import cn.scauaie.model.dao.FormDO;
import cn.scauaie.model.query.FormQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FormDO record);

    int insertIfOpenidNotExist(FormDO record);

    FormDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FormDO record);

    int updateByPrimaryKey(FormDO record);

    FormDO selectByOpenid(String openid);

    String selectAvatarById(Integer id);

    List<FormDO> listByFormQuery(FormQuery formQuery);

    List<DepNumberDO> listFirstDepNumbers();

    List<DepNumberDO> listSecondDepNumbers();

    List<DepNumberDO> listDepNumbersIfFirstDepSameAsSecondDep();

    int getCountById(Integer id);

    int updateAvatar(@Param("id") Integer id, @Param("avatar") String avatar);

    String getName(Integer id);
}