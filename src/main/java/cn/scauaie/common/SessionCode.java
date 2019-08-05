package cn.scauaie.common;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-02-28 20:42
 */
public enum SessionCode {

    LOGINING("LOGINING", 0);


    private final String name;
    private final Integer code;

    SessionCode(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {return name;}

    public Integer getCode() {
        return code;
    }


}
