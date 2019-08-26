package cn.scauaie.model.dao;

import java.util.Date;

public class EvaluationLogDO {
    private Integer id;

    private Integer iid;

    private Integer fid;

    private Integer eid;

    private String message;

    private Date createTime;

    private Date updateTime;

    public EvaluationLogDO(Integer id, Integer iid, Integer fid, Integer eid, String message, Date createTime, Date updateTime) {
        this.id = id;
        this.iid = iid;
        this.fid = fid;
        this.eid = eid;
        this.message = message;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public EvaluationLogDO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
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
        return "EvaluationLogDO{" +
                "id=" + id +
                ", iid=" + iid +
                ", fid=" + fid +
                ", eid=" + eid +
                ", message='" + message + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}