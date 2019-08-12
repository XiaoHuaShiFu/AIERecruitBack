package cn.scauaie.model.ao;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-11 11:23
 */
public class TokenAO {

    private String token;
    private Value value;

    public TokenAO() {
    }

    public TokenAO(String token, String type, Integer id) {
        this.token = token;
        value = new Value(type, id);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    /**
     * TokenAO的值对象
     */
    public static class Value {
        private String type;
        private Integer id;

        public Value() {
        }

        public Value(String type, Integer id) {
            this.type = type;
            this.id = id;
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

        @Override
        public String toString() {
            return "Value{" +
                    "type='" + type + '\'' +
                    ", id=" + id +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "TokenAO{" +
                "token='" + token + '\'' +
                ", value=" + value +
                '}';
    }
}
