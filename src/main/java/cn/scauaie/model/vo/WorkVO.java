package cn.scauaie.model.vo;

public class WorkVO {
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
        return "WorkVO{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}