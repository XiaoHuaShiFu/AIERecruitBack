package cn.scauaie.model.query;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-14 16:38
 */
public class LogQuery {
    Integer pageNum;

    Integer pageSize;

    String logType;

    public LogQuery() {
    }

    public LogQuery(Integer pageNum, Integer pageSize, String logType) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.logType = logType;
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

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    @Override
    public String toString() {
        return "LogQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", logType='" + logType + '\'' +
                '}';
    }
}


