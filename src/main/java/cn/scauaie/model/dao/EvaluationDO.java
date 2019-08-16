package cn.scauaie.model.dao;

import java.util.Date;

public class EvaluationDO {
    private Integer id;

    private Integer fid;

    private Integer iid;

    private String evaluation;

    private Boolean pass;

    private Date createTime;

    private Date updateTime;

    public EvaluationDO() {
    }

    public EvaluationDO(Integer id, Integer fid, Integer iid, String evaluation, Boolean pass) {
        this.id = id;
        this.fid = fid;
        this.iid = iid;
        this.evaluation = evaluation;
        this.pass = pass;
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

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
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

    @Override
    public String toString() {
        return "EvaluationDO{" +
                "id=" + id +
                ", fid=" + fid +
                ", iid=" + iid +
                ", evaluation='" + evaluation + '\'' +
                ", pass=" + pass +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}