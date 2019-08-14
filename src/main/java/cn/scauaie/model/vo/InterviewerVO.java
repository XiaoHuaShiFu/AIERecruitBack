package cn.scauaie.model.vo;

public class InterviewerVO {
    private Integer id;

    private String name;

    private String dep;

    public InterviewerVO() {
    }

    public InterviewerVO(Integer id, String name, String dep) {
        this.id = id;
        this.name = name;
        this.dep = dep;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "InterviewerVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dep='" + dep + '\'' +
                '}';
    }
}