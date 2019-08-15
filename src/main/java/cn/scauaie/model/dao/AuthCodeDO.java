package cn.scauaie.model.dao;

import java.util.Date;

public class AuthCodeDO {
    private String code;
    private String dep;

    public AuthCodeDO() {
    }

    public AuthCodeDO(String code, String dep) {
        this.code = code;
        this.dep = dep;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    @Override
    public String toString() {
        return "AuthCodeDO{" +
                "code='" + code + '\'' +
                ", dep='" + dep + '\'' +
                '}';
    }
}