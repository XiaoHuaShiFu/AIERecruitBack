package cn.scauaie.model.vo;

public class AuthCodeVO {
    private String code;
    private String dep;

    public AuthCodeVO() {
    }

    public AuthCodeVO(String code, String dep) {
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
        return "AuthCodeVO{" +
                "code='" + code + '\'' +
                ", dep='" + dep + '\'' +
                '}';
    }
}