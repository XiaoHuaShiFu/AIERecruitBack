package cn.scauaie.manager;

import cn.scauaie.model.dto.Code2SessionDTO;
import cn.scauaie.utils.ObjectAnalyzer;
import cn.scauaie.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 描述: 微信小程序通用接口
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 16:51
 */
@Component("weChatMpManager")
public class WeChatMpManager {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 请求code2Session的url
     */
    private final String AUTH_CODE2SESSION_URL;

    /**
     * 小程序 appId
     */
    private final String APP_ID;

    /**
     * 小程序 appSecret
     */
    private final String APP_SECRET;

    /**
     * 授权类型
     */
    private final String GRANT_TYPE;

    /**
     * 初始化微信小程序相关常量
     */
    public WeChatMpManager() {
        PropertiesUtil propertiesUtil = new PropertiesUtil("weChat.properties");
        AUTH_CODE2SESSION_URL = propertiesUtil.getProperty("auth.code2Session.url");
        APP_ID = propertiesUtil.getProperty("app.id");
        APP_SECRET = propertiesUtil.getProperty("app.secret");
        GRANT_TYPE= propertiesUtil.getProperty("grant.type");
    }

    /**
     * 通过code获取封装过的Code2SessionDTO
     * @param code String
     * @return Code2SessionDTO
     */
    public Code2SessionDTO getCode2SessionByCode(String code) {
        ResponseEntity<Code2SessionDTO> responseEntity = restTemplate.getForEntity(
                AUTH_CODE2SESSION_URL + "?appid=" + APP_ID + "&secret=" + APP_SECRET + "&js_code=" + code + "&grant_type=" + GRANT_TYPE,
                Code2SessionDTO.class);
        return responseEntity.getBody();
    }

    /**
     * 通过code获取openid
     * @param code String
     * @return String
     */
    public String getOpenidByCode(String code) {
        Code2SessionDTO code2SessionDTO = getCode2SessionByCode(code);
        return code2SessionDTO.getOpenid();
    }

}
