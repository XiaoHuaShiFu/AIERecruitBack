package cn.scauaie.manager.impl;

import cn.scauaie.manager.WeChatMpManager;
import cn.scauaie.manager.constant.WeChatGrantTypeEnum;
import cn.scauaie.manager.constant.WeChatMp;
import cn.scauaie.manager.constant.WeChatMpConsts;
import cn.scauaie.model.dto.AccessTokenDTO;
import cn.scauaie.model.dto.Code2SessionDTO;
import cn.scauaie.service.CacheService;
import cn.scauaie.service.constant.RedisStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * 描述: 微信小程序通用接口
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 16:51
 */
@Component("weChatMpManager")
public class WeChatMpManagerImpl implements WeChatMpManager {

    private static final Logger logger = LoggerFactory.getLogger(WeChatMpManagerImpl.class);

    @Autowired
    private CacheService cacheService;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 招新小程序access-token的redis key名
     */
    private static final String REDIS_KEY = "recruit:wechat:mp:access:token";

    /**
     * 获取access-token
     *
     * @return AccessTokenDTO
     */
    public Optional<String> getAccessToken() {
        String accessToken = cacheService.get(REDIS_KEY);
        if (!accessToken.equals(RedisStatus.OK.name())) {
            logger.warn("Get access token fail.");
            return Optional.empty();
        }

        return Optional.of(accessToken);
    }


    /**
     * 获取新的access-token
     *
     * @return AccessTokenDTO
     */
    public Optional<AccessTokenDTO> getNewAccessToken() {
        // 获取access-token
        String url = WeChatMpConsts.ACCESS_TOKEN_URL + "?grant_type=" + WeChatGrantTypeEnum.CLIENT_CREDENTIAL.getValue() +
                "&appid=" + WeChatMp.AIE_RECRUIT.getAppId() + "&secret=" + WeChatMp.AIE_RECRUIT.getSecret();
        ResponseEntity<AccessTokenDTO> entity = restTemplate.getForEntity(url, AccessTokenDTO.class);
        if (entity.getBody().getErrcode() == null) {
            logger.warn("Get access token fail.");
            return Optional.empty();
        }

        //添加到redis
        String status = cacheService.set(REDIS_KEY, entity.getBody().getAccess_token());
        if (!status.equals(RedisStatus.OK.name())) {
            logger.warn("Get access token fail.");
            return Optional.empty();
        }

        return Optional.of(entity.getBody());
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

    /**
     * 通过code获取封装过的Code2SessionDTO
     * @param code String
     * @param weChatMp 小程序类别
     * @return Code2SessionDTO
     */
    private Code2SessionDTO getCode2Session(String code, WeChatMp weChatMp) {
        String url = WeChatMpConsts.CODE2SESSION_URL + "?appid=" + weChatMp.getAppId() + "&secret=" + weChatMp.getSecret()
                + "&js_code=" + code + "&grant_type=" + WeChatMpConsts.GRANT_TYPE;
        ResponseEntity<Code2SessionDTO> responseEntity = restTemplate.getForEntity(url, Code2SessionDTO.class);
        return responseEntity.getBody();
    }

}
