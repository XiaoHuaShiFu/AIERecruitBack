package cn.scauaie.manager;

import cn.scauaie.manager.constant.WeChatMp;
import cn.scauaie.manager.constant.WeChatMpConsts;
import cn.scauaie.model.dto.Code2SessionDTO;
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
     * 通过code获取封装过的Code2SessionDTO
     * @param code String
     * @param weChatMp 小程序类别
     * @return Code2SessionDTO
     */
    public Code2SessionDTO getCode2Session(String code, WeChatMp weChatMp) {
        String url = WeChatMpConsts.APP_URL + "?appid=" + weChatMp.getAppId() + "&secret=" + weChatMp.getSecret()
                + "&js_code=" + code + "&grant_type=" + WeChatMpConsts.GRANT_TYPE;
        ResponseEntity<Code2SessionDTO> responseEntity = restTemplate.getForEntity(url, Code2SessionDTO.class);
        return responseEntity.getBody();
    }

    /**
     * 通过code获取openid
     * @param code String
     * @param mpName 小程序名
     * @return String
     */
    public String getOpenid(String code, String mpName) {
        WeChatMp weChatMp = WeChatMp.valueOf(mpName);
        return getOpenid(code, weChatMp);
    }

    /**
     * 通过code获取openid
     * @param code String
     * @param weChatMp 小程序类别
     * @return String
     */
    public String getOpenid(String code, WeChatMp weChatMp) {
        Code2SessionDTO code2SessionDTO = getCode2Session(code, weChatMp);
        return code2SessionDTO.getOpenid();
    }

}
