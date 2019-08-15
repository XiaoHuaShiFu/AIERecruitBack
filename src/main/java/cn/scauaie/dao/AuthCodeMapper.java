package cn.scauaie.dao;

import cn.scauaie.model.dao.AuthCodeDO;

public interface AuthCodeMapper {
    AuthCodeDO getAuthCodeByCode(String code);

    int deleteAuthCodeByCode(String code);
}