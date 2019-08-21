package cn.scauaie.dao;

import cn.scauaie.model.dao.AuthCodeDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthCodeMapper {
    AuthCodeDO getAuthCodeByCode(String code);

    int deleteAuthCodeByCode(String code);

    int insertAuthCodes(@Param("authCodeList") List<AuthCodeDO> authCodeList);
}