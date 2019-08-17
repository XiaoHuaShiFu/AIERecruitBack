package cn.scauaie.model.vo;

public class QueuerVO {
    private Integer fid;

    private String name;

    private String dep;

    private Integer state;

    private Integer expectedWaitTime;

    private Integer frontNumber;

    public QueuerVO() {
    }

    public QueuerVO(Integer fid, String name, String dep, Integer state, Integer expectedWaitTime, Integer frontNumber) {
        this.fid = fid;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
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
        return "QueuerVO{" +
                "fid=" + fid +
                ", name='" + name + '\'' +
                ", dep='" + dep + '\'' +
                ", state=" + state +
                ", expectedWaitTime=" + expectedWaitTime +
                ", frontNumber=" + frontNumber +
                '}';
    }
}

