package cn.scauaie.model.query;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-14 16:38
 */
public class EvaluationQuery {
    Integer pageNum;

    Integer pageSize;

    Boolean pass;

    Integer fid;

    String interviewerDep;

    public EvaluationQuery() {
    }

    public EvaluationQuery(Integer pageNum, Integer pageSize, Boolean pass, Integer fid, String interviewerDep) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pass = pass;
        this.fid = fid;
        this.interviewerDep = interviewerDep;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getInterviewerDep() {
        return interviewerDep;
    }

    public void setInterviewerDep(String interviewerDep) {
        this.interviewerDep = interviewerDep;
    }

    @Override
    public String toString() {
        return "EvaluationQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", pass=" + pass +
                ", fid=" + fid +
                ", interviewerDep='" + interviewerDep + '\'' +
                '}';
    }
}


