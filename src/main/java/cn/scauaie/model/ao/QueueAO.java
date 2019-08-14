package cn.scauaie.model.ao;

public class QueueAO {
    private Integer fid;

    private String dep;

    private String state;

    private Integer expectedWaitTime;

    private Integer frontNumber;

    public QueueAO() {
    }

    public QueueAO(Integer fid, String dep, String state, Integer expectedWaitTime, Integer frontNumber) {
        this.fid = fid;
        this.dep = dep;
        this.state = state;
        this.expectedWaitTime = expectedWaitTime;
        this.frontNumber = frontNumber;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getExpectedWaitTime() {
        return expectedWaitTime;
    }

    public void setExpectedWaitTime(Integer expectedWaitTime) {
        this.expectedWaitTime = expectedWaitTime;
    }

    public Integer getFrontNumber() {
        return frontNumber;
    }

    public void setFrontNumber(Integer frontNumber) {
        this.frontNumber = frontNumber;
    }

    @Override
    public String toString() {
        return "QueueAO{" +
                "fid=" + fid +
                ", dep='" + dep + '\'' +
                ", state='" + state + '\'' +
                ", expectedWaitTime=" + expectedWaitTime +
                ", frontNumber=" + frontNumber +
                '}';
    }

}

