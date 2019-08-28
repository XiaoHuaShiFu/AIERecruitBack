package cn.scauaie.model.dao;

import java.util.Date;

public class ResultQrcodeDO {
    private Integer id;

    private String dep;

    private String qrcode;

    private Integer rid;

    private Date createTime;

    private Date updateTime;

    public ResultQrcodeDO(Integer id, String dep, String qrcode, Integer rid) {
        this.id = id;
        this.dep = dep;
        this.qrcode = qrcode;
        this.rid = rid;
    }

    public ResultQrcodeDO() {
        super();
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
        this.dep = dep == null ? null : dep.trim();
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode == null ? null : qrcode.trim();
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ResultQrcodeDO{" +
                "id=" + id +
                ", dep='" + dep + '\'' +
                ", qrcode='" + qrcode + '\'' +
                ", rid=" + rid +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}