package cn.scauaie.model.ao;

import cn.scauaie.model.ao.group.GroupEvaluationAO;
import cn.scauaie.model.ao.group.GroupEvaluationAOPOST;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EvaluationAO {

    @Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.",
            value = 0,
            groups = {GroupEvaluationAO.class})
    private Integer id;

    @Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of fid below, min: 0.",
            value = 0,
            groups = {GroupEvaluationAO.class})
    private Integer fid;

    @Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.",
            value = 0,
            groups = {GroupEvaluationAO.class})
    private Integer iid;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The evaluation must be not blank.",
            groups = {GroupEvaluationAOPOST.class})
    @Size(message = "INVALID_PARAMETER_SIZE: The size of evaluation must be from 1 to 200.",
            min = 1, max = 200,
            groups = {GroupEvaluationAO.class})
    private String evaluation;

    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required pass must be not null.",
            groups = {GroupEvaluationAOPOST.class})
    private Boolean pass;

    private FormAO form;

    private InterviewerAO interviewer;

    public EvaluationAO() {
    }

    public EvaluationAO(Integer id, Integer fid, Integer iid, String evaluation, Boolean pass,
                        FormAO form, InterviewerAO interviewer) {
        this.id = id;
        this.fid = fid;
        this.iid = iid;
        this.evaluation = evaluation;
        this.pass = pass;
        this.form = form;
        this.interviewer = interviewer;
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

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public FormAO getForm() {
        return form;
    }

    public void setForm(FormAO form) {
        this.form = form;
    }

    public InterviewerAO getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(InterviewerAO interviewer) {
        this.interviewer = interviewer;
    }

    @Override
    public String toString() {
        return "EvaluationAO{" +
                "id=" + id +
                ", fid=" + fid +
                ", iid=" + iid +
                ", evaluation='" + evaluation + '\'' +
                ", pass=" + pass +
                ", form=" + form +
                ", interviewer=" + interviewer +
                '}';
    }
}