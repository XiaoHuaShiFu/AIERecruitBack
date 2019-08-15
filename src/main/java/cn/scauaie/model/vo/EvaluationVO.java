package cn.scauaie.model.vo;

public class EvaluationVO {
    private Integer id;

    private String evaluation;

    private Boolean pass;

    private FormVO form;

    private InterviewerVO interviewer;

    public EvaluationVO() {
    }

    public EvaluationVO(Integer id, String evaluation, Boolean pass, FormVO form, InterviewerVO interviewer) {
        this.id = id;
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

    public FormVO getForm() {
        return form;
    }

    public void setForm(FormVO form) {
        this.form = form;
    }

    public InterviewerVO getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(InterviewerVO interviewer) {
        this.interviewer = interviewer;
    }

    @Override
    public String toString() {
        return "EvaluationVO{" +
                "id=" + id +
                ", evaluation='" + evaluation + '\'' +
                ", pass=" + pass +
                ", form=" + form +
                ", interviewer=" + interviewer +
                '}';
    }
}