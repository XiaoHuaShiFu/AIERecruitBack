package cn.scauaie.model.vo;

public class ResultVO {
    private Integer id;

    private Integer fid;

    private String result;

    public ResultVO() {
    }

    public ResultVO(Integer id, Integer fid, String result) {
        this.id = id;
        this.fid = fid;
        this.result = result;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultVO{" +
                "id=" + id +
                ", fid=" + fid +
                ", result='" + result + '\'' +
                '}';
    }
}