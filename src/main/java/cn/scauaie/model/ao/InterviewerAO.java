package cn.scauaie.model.ao;

public class InterviewerAO {
    private Integer id;

    private String name;

    private String dep;

    public InterviewerAO() {
    }

    public InterviewerAO(Integer id, String name, String dep) {
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
        return "InterviewerAO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dep='" + dep + '\'' +
                '}';
    }
}