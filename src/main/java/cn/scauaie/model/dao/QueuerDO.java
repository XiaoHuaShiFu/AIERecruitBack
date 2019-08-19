package cn.scauaie.model.dao;

import java.util.Date;

public class QueuerDO {
    private Integer id;

    private Integer fid;

    private String dep;

    private String state;

    private Date createTime;

    private Date updateTime;

    public QueuerDO() {
    }

    public QueuerDO(Integer id, Integer fid, String dep, String state) {
        this.id = id;
        this.fid = fid;
        this.dep = dep;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
        return "QueuerDO{" +
                "id=" + id +
                ", fid=" + fid +
                ", dep='" + dep + '\'' +
                ", state='" + state + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}