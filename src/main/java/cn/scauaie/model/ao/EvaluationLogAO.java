package cn.scauaie.model.ao;

public class EvaluationLogAO {
    private Integer id;

    private Integer iid;

    private Integer fid;

    private Integer eid;

    private String message;

    public EvaluationLogAO() {
    }

    public EvaluationLogAO(Integer id, Integer iid, Integer fid, Integer eid, String message) {
        this.id = id;
        this.iid = iid;
        this.fid = fid;
        this.eid = eid;
        this.message = message;
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
        this.message = message;
    }

    @Override
    public String toString() {
        return "EvaluationLogAO{" +
                "id=" + id +
                ", iid=" + iid +
                ", fid=" + fid +
                ", eid=" + eid +
                ", message='" + message + '\'' +
                '}';
    }
}