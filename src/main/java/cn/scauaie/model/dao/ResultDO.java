package cn.scauaie.model.dao;

import java.util.Date;

public class ResultDO {
    private Integer id;

    private Integer fid;

    private String result;

    private Date createTime;

    private Date updateTime;

    public ResultDO(Integer id, Integer fid, String result, Date createTime, Date updateTime) {
        this.id = id;
        this.fid = fid;
        this.result = result;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public ResultDO() {
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
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
        return "ResultDO{" +
                "id=" + id +
                ", fid=" + fid +
                ", result='" + result + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}