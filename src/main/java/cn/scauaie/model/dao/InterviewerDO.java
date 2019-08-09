package cn.scauaie.model.dao;

import java.util.Date;

public class InterviewerDO {
    private Integer id;

    private String openid;

    private String name;

    private String dep;

    private Date createTime;

    private Date updateTime;

    public InterviewerDO(Integer id, String openid, String name, String dep, Date createTime, Date updateTime) {
        this.id = id;
        this.openid = openid;
        this.name = name;
        this.dep = dep;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public InterviewerDO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep == null ? null : dep.trim();
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