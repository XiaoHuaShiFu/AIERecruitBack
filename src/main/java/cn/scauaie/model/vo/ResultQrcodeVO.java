package cn.scauaie.model.vo;

public class ResultQrcodeVO {
    private String dep;

    private String qrcode;

    public ResultQrcodeVO() {
    }

    public ResultQrcodeVO(String dep, String qrcode) {
        this.dep = dep;
        this.qrcode = qrcode;
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
        return "ResultQrcodeVO{" +
                "dep='" + dep + '\'' +
                ", qrcode='" + qrcode + '\'' +
                '}';
    }
}