package cn.scauaie.model.dao;

import java.util.Date;

public class QueueDO {
    private Integer id;

    private Integer fid;

    private String dep;

    private String state;

    private Date createTime;

    private Date updateTime;

    public QueueDO(Integer id, Integer fid, String dep, String state, Date createTime, Date updateTime) {
        this.id = id;
        this.fid = fid;
        this.dep = dep;
        this.state = state;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public QueueDO() {
        super();
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
        this.dep = dep == null ? null : dep.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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
        return "QueueDO{" +
                "id=" + id +
                ", fid=" + fid +
                ", dep='" + dep + '\'' +
                ", state='" + state + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}