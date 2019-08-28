package cn.scauaie.model.ao;

import cn.scauaie.model.ao.group.*;
import cn.scauaie.model.dao.ResultQrcodeDO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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
    @Size(message = "INVALID_PARAMETER_SIZE: The size of result must be from 1 to 200.",
            min = 1, max = 200,
            groups = {Group.class})
    private String result;

    private List<ResultQrcodeAO> qrcodes;

    public ResultAO() {
    }

    public ResultAO(@Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.",
            value = 0,
            groups = {Group.class}) Integer id, @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required fid must be not null.",
            groups = {GroupPOST.class}) @Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of fid below, min: 0.",
            value = 0,
            groups = {Group.class}) Integer fid, @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The result must be not blank.",
            groups = {GroupPOST.class}) @Size(message = "INVALID_PARAMETER_SIZE: The size of result must be from 1 to 200.",
            min = 1, max = 200,
            groups = {Group.class}) String result, List<ResultQrcodeAO> qrcodes) {
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

    public List<ResultQrcodeAO> getQrcodes() {
        return qrcodes;
    }

    public void setQrcodes(List<ResultQrcodeAO> qrcodes) {
        this.qrcodes = qrcodes;
    }

    @Override
    public String toString() {
        return "ResultAO{" +
                "id=" + id +
                ", fid=" + fid +
                ", result='" + result + '\'' +
                ", qrcodes=" + qrcodes +
                '}';
    }
}