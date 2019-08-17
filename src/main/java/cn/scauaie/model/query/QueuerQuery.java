package cn.scauaie.model.query;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-14 16:38
 */
public class QueuerQuery {
    Integer pageNum;

    Integer pageSize;

    String dep;

    public QueuerQuery() {
    }

    public QueuerQuery(Integer pageNum, Integer pageSize, String dep) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.dep = dep;
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

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    @Override
    public String toString() {
        return "QueuerQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", dep='" + dep + '\'' +
                '}';
    }
}


