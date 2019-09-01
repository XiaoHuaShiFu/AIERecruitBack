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

    FormDO getForm(Integer id);

    int updateByPrimaryKeySelective(FormDO record);

    int updateByPrimaryKey(FormDO record);

    FormDO getFormByOpenid(String openid);

    String getAvatar(Integer id);

    List<FormDO> listByFormQuery(FormQuery formQuery);

    List<DepNumberDO> listFirstDepNumbers();

    List<DepNumberDO> listSecondDepNumbers();

    List<DepNumberDO> listDepNumbersIfFirstDepSameAsSecondDep();

    int getCount(Integer id);

    int getCountByOpenid(String openid);

    int updateAvatar(@Param("id") Integer id, @Param("avatar") String avatar);

    String getName(Integer id);

    String getOpenid(Integer id);

    String getFirstDep(Integer id);

}