package cn.scauaie.model.query;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-14 16:38
 */
public class ResultQuery {
    Integer pageNum;

    Integer pageSize;

    Integer fid;

    public ResultQuery() {
    }

    public ResultQuery(Integer pageNum, Integer pageSize, Integer fid) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.fid = fid;
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

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    @Override
    public String toString() {
        return "ResultQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", fid=" + fid +
                '}';
    }
}


