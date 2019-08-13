package cn.scauaie.service.constant;

/**
 * 描述: token的类型
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-11 11:30
 */
public enum TokenType {

    /**
     * Form-token
     */
    FORM("form"),

    /**
     * Interviewer-token
     */
    INTERVIEWER("interviewer");


    private final String type;

    TokenType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
