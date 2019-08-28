package cn.scauaie.model.ao;

public class ResultQrcodeAO {
    private Integer id;

    private String dep;

    private String qrcode;

    public ResultQrcodeAO() {
    }

    public ResultQrcodeAO(Integer id, String dep, String qrcode) {
        this.id = id;
        this.dep = dep;
        this.qrcode = qrcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    @Override
    public String toString() {
        return "ResultQrcodeAO{" +
                "id=" + id +
                ", dep='" + dep + '\'' +
                ", qrcode='" + qrcode + '\'' +
                '}';
    }
}