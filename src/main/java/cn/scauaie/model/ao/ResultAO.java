package cn.scauaie.model.ao;

import cn.scauaie.model.ao.group.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ResultAO {

    @Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.",
            value = 0,
            groups = {Group.class})
    private Integer id;

    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required fid must be not null.",
            groups = {GroupPOST.class})
    @Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of fid below, min: 0.",
            value = 0,
            groups = {Group.class})
    private Integer fid;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The result must be not blank.",
            groups = {GroupPOST.class})
    @Size(message = "INVALID_PARAMETER_SIZE: The size of result must be from 1 to 20.",
            min = 1, max = 500,
            groups = {Group.class})
    private String result;

    public ResultAO() {
    }

    public ResultAO(Integer id, Integer fid, String result) {
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