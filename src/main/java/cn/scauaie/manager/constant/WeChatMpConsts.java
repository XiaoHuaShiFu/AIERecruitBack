package cn.scauaie.manager.constant;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-13 18:38
 */
public final class WeChatMpConsts {

    private WeChatMpConsts() {}

    /**
     * 请求code2Session的url
     */
    public static final String CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * 获取access_token的url
     */
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    /**
     * 授权类型
     */
    public static final String GRANT_TYPE = "authorization_code";

    /**
     * 面试结果通知模板id
     */
    public static final String INTERVIEW_RESULT_NOTIFACATION_TEMPLATE_ID =
            "ZiNM38LGvpZMWVlAR13WDJVNBzV2sZ6ry4-Sv9XxsCY";
}
