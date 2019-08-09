package cn.scauaie.model.dao;

import java.util.Date;

public class WorkDO {
    private Integer id;

    private Integer fid;

    private String url;

    private Date createTime;

    private Date updateTime;

    public WorkDO(Integer id, Integer fid, String url, Date createTime, Date updateTime) {
        this.id = id;
        this.fid = fid;
        this.url = url;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public WorkDO() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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
}