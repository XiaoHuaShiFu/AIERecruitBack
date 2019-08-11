package cn.scauaie.model.dao;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-11 11:23
 */
public class TokenDO {

    private String token;
    private String type;
    private Integer id;

    public TokenDO() {
    }

    public TokenDO(String type, Integer id) {
        this.type = type;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
