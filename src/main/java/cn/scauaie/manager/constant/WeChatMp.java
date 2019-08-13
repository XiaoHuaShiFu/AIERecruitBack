package cn.scauaie.manager.constant;

/**
 * 描述: 微信小程序名字常量
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-13 19:23
 */
public enum WeChatMp {

    /**
     * 华农科联小程序相关常量
     */
    SCAU_AIE("scauaie", "wx5f8153b583418ff9", "99ffbd8b67bc02cb9934972fbd0363b2"),

    /**
     * 科联招新小程序相关常量
     */
    AIE_RECRUIT("aierecuit", "wxae6e8e372df8d23b", "efbab90f6405d6d9711e45e180d0586a");

    /**
     * 微信小程序名字
     */
    private final String name;

    /**
     * 微信小程序appid
     */
    private final String appId;

    /**
     * 微信小程序secret
     */
    private final String secret;

    WeChatMp(String name, String appId, String secret) {
        this.name = name;
        this.appId = appId;
        this.secret = secret;
    }

    public String getName() {
        return name;
    }

    public String getAppId() {
        return appId;
    }

    public String getSecret() {
        return secret;
    }

}
