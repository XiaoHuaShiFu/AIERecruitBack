package cn.scauaie.manager.impl;

import cn.scauaie.manager.WeChatMpManager;
import cn.scauaie.manager.constant.WeChatGrantTypeEnum;
import cn.scauaie.manager.constant.WeChatMp;
import cn.scauaie.manager.constant.WeChatMpConsts;
import cn.scauaie.model.dto.AccessTokenDTO;
import cn.scauaie.model.dto.Code2SessionDTO;
import cn.scauaie.model.dto.MessageTemplateDTO;
import cn.scauaie.model.dto.WeChatMpResponseDTO;
import cn.scauaie.service.CacheService;
import cn.scauaie.service.constant.RedisStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
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
        if (accessToken == null) {
            logger.warn("Get access token fail.");
            return Optional.empty();
        }
        return Optional.of(accessToken);
    }

    /**
     * 获取新的access-token
     * 并添加到redis
     * 并设置过期时间
     *
     * @return AccessTokenDTO
     */
    public Optional<AccessTokenDTO> getNewAccessToken() {
        // 获取access-token
        String url = MessageFormat.format("{0}?grant_type={1}&appid={2}&secret={3}",
                WeChatMpConsts.ACCESS_TOKEN_URL, WeChatGrantTypeEnum.CLIENT_CREDENTIAL.getValue(),
                WeChatMp.AIE_RECRUIT.getAppId(), WeChatMp.AIE_RECRUIT.getSecret());
        ResponseEntity<AccessTokenDTO> entity = restTemplate.getForEntity(url, AccessTokenDTO.class);
        if (entity.getBody().getAccess_token() == null) {
            logger.warn("Get access token fail.");
            return Optional.empty();
        }

        // 添加到redis
        String status = cacheService.set(REDIS_KEY, entity.getBody().getAccess_token());
        if (!status.equals(RedisStatus.OK.name())) {
            logger.warn("Set access token fail.");
            return Optional.empty();
        }

        // 设置access-token在redis的过期时间
        Long expire = cacheService.expire(REDIS_KEY, entity.getBody().getExpires_in());
        if (expire == 0) {
            logger.warn("Set redis expire fail. {}", entity.getBody().getAccess_token());
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
     * 发送模板消息
     *
     * @param messageTemplate 模板消息
     * @return 是否发送成功
     */
    public boolean sendTemplateMessage(MessageTemplateDTO messageTemplate) {
        Optional<String> accessToken = getAccessToken();
        if (!accessToken.isPresent()) {
            return false;
        }

        String url = MessageFormat.format(WeChatMpConsts.TEMPLATE_MESSAGE_URL + "?access_token={0}",
                accessToken.get());
        ResponseEntity<WeChatMpResponseDTO> responseEntity = restTemplate.postForEntity(url, messageTemplate, WeChatMpResponseDTO.class);
        System.out.println(responseEntity.getBody());
        return responseEntity.getBody().getErrcode().equals(0);
    }

    /**
     * 通过code获取封装过的Code2SessionDTO
     * @param code String
     * @param weChatMp 小程序类别
     * @return Code2SessionDTO
     */
    private Code2SessionDTO getCode2Session(String code, WeChatMp weChatMp) {
        String url = MessageFormat.format("{0}?appid={1}&secret={2}&js_code={3}&grant_type={4}",
                WeChatMpConsts.CODE2SESSION_URL, weChatMp.getAppId(),
                weChatMp.getSecret(), code, WeChatGrantTypeEnum.AUTHORIZATION_CODE.getValue());
        ResponseEntity<Code2SessionDTO> responseEntity = restTemplate.getForEntity(url, Code2SessionDTO.class);
        return responseEntity.getBody();
    }

}
