package cn.scauaie.manager;

import cn.scauaie.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 描述: 微信小程序通用接口
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 16:51
 */
@Component("weChatMpManager")
public class WeChatMpManager {

    public static void main(String[] args) {
        WeChatMpManager weChatMpManager = new WeChatMpManager();
    }

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    private final String APP_URL;
    private final String APP_ID;
    private final String APP_SECRET;
    private final String GRANT_TYPE;

    /**
     * 初始化微信小程序相关常量
     */
    public WeChatMpManager() {
        PropertiesUtil propertiesUtil = new PropertiesUtil("weChat.properties");
        APP_URL = propertiesUtil.getProperty("app.url");
        APP_ID = propertiesUtil.getProperty("app.id");
        APP_SECRET = propertiesUtil.getProperty("app.secret");
        GRANT_TYPE= propertiesUtil.getProperty("grant.type");
    }

    public void getOpenidByCode(String code) {
        System.out.println(restTemplate.getForEntity(
                APP_URL + "?appid=" + APP_ID + "&secret=" + APP_SECRET + "&js_code=" + code + "&grant_type=" + GRANT_TYPE,
                Map.class));
    }

}
