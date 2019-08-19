package cn.scauaie.model.bo;

import java.util.Objects;

public class QueuerBO {
    private Integer fid;

    private String name;

    private String dep;

    private String state;

    private Integer expectedWaitTime;

    private Integer frontNumber;

    public QueuerBO() {
    }

    public QueuerBO(Integer fid, String name, String dep, String state, Integer expectedWaitTime, Integer frontNumber) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueuerBO queuerBO = (QueuerBO) o;
        return fid.equals(queuerBO.fid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fid);
    }

    @Override
    public String toString() {
        return "QueueBO{" +
                "fid=" + fid +
                ", name='" + name + '\'' +
                ", dep='" + dep + '\'' +
                ", state=" + state +
                ", expectedWaitTime=" + expectedWaitTime +
                ", frontNumber=" + frontNumber +
                '}';
    }
}

