package cn.scauaie.model.ao;

public class WorkAO {
    private Integer id;

    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "WorkAO{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}