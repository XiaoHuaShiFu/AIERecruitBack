package cn.scauaie.model.dao;

import java.util.Date;

public class EvaluationDO {
    private Integer id;

    private Integer fid;

    private String name;

    private String evaluation;

    private Boolean pass;

    private Date createTime;

    private Date updateTime;

    public EvaluationDO(Integer id, Integer fid, String name, String evaluation, Boolean pass, Date createTime, Date updateTime) {
        this.id = id;
        this.fid = fid;
        this.name = name;
        this.evaluation = evaluation;
        this.pass = pass;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public EvaluationDO() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation == null ? null : evaluation.trim();
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
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