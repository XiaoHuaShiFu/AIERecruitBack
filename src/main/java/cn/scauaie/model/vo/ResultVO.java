package cn.scauaie.model.vo;

import java.util.List;

public class ResultVO {
    private Integer id;

    private Integer fid;

    private String result;

    private List<ResultQrcodeVO> qrcodes;

    public ResultVO() {
    }

    public ResultVO(Integer id, Integer fid, String result, List<ResultQrcodeVO> qrcodes) {
        this.id = id;
        this.fid = fid;
        this.result = result;
        this.qrcodes = qrcodes;
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

    public List<ResultQrcodeVO> getQrcodes() {
        return qrcodes;
    }

    public void setQrcodes(List<ResultQrcodeVO> qrcodes) {
        this.qrcodes = qrcodes;
    }

    @Override
    public String toString() {
        return "ResultVO{" +
                "id=" + id +
                ", fid=" + fid +
                ", result='" + result + '\'' +
                ", qrcodes=" + qrcodes +
                '}';
    }
}