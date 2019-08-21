package cn.scauaie.model.ao;

public class AuthCodeAO {
    private String code;
    private String dep;

    public AuthCodeAO() {
    }

    public AuthCodeAO(String code, String dep) {
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
        return "AuthCodeAO{" +
                "code='" + code + '\'' +
                ", dep='" + dep + '\'' +
                '}';
    }
}