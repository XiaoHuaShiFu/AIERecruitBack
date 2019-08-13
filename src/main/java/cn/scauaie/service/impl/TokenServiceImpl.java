package cn.scauaie.service.impl;

import cn.scauaie.error.ErrorCode;
import cn.scauaie.exception.ProcessingException;
import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.service.CacheService;
import cn.scauaie.service.TokenService;
import cn.scauaie.service.constant.RedisStatus;
import cn.scauaie.service.constant.TokenExpire;
import cn.scauaie.service.constant.TokenType;
import cn.scauaie.util.SHA256;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private Gson gson;

    /**
     * 创建并保存Form-token
     *
     * @param code wx.login()接口获取的返回值
     * @param formId 报名表编号
     * @param dep 部门
     * @return token
     */
    @Override
    public String createAndSaveFormToken(String code, Integer formId, String dep) {
        String token = createToken();
        //装配成TokenAO
        TokenAO tokenAO = new TokenAO(token, TokenType.FORM, formId, dep);

        //保存form-token
        saveFormToken(tokenAO);

        return token;
    }

    /**
     * 认证token并设置过期时间
     *
     * @param request HttpServletRequest
     * @return TokenAO
     */
    @Override
    public TokenAO authTokenAndSetExpire(HttpServletRequest request) {
        TokenAO tokenAO = authToken(request);
        cacheService.expire(tokenAO.getToken(), TokenExpire.NORMAL.getExpire());
        return tokenAO;
    }

    /**
     * 认证form-token并设置过期时间
     *
     * @param request HttpServletRequest
     * @return TokenAO
     */
    @Override
    public TokenAO authFormTokenAndSetExpire(HttpServletRequest request) {
        TokenAO tokenAO = authToken(request);
        //如果token的类型不是form-token
        if (tokenAO.getType() != TokenType.FORM) {
            throw new ProcessingException(ErrorCode.FORBIDDEN_SUB_USER);
        }

        //更新token过期时间
        cacheService.expire(tokenAO.getToken(), TokenExpire.NORMAL.getExpire());

        return tokenAO;
    }

    /**
     * 在缓存里添加from-token
     *
     * @param tokenAO 报名表id
     */
    private void saveFormToken(TokenAO tokenAO) {
        //保存form-token
        String code = cacheService.set(tokenAO.getToken(), gson.toJson(tokenAO));

        //如果保存失败
        if (!code.equals(RedisStatus.OK.name())) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Failed to create form-token.");
        }

        //设置过期时间
        cacheService.expire(tokenAO.getToken(), TokenExpire.NORMAL.getExpire());
    }

    /**
     * 创建token
     *
     * @return token
     */
    private String createToken() {
        return SHA256.encryption(UUID.randomUUID().toString());
    }

    /**
     * 认证token
     *
     * @param request HttpServletRequest
     * @return TokenAO
     */
    private TokenAO authToken(HttpServletRequest request){
        String token = request.getHeader("authorization");
        //token不存在
        if (token == null) {
            throw new ProcessingException(ErrorCode.UNAUTHORIZED_TOKEN_IS_NULL);
        }

        String jsonToken = cacheService.get(token);
        //token不存在
        if (jsonToken == null) {
            throw new ProcessingException(ErrorCode.UNAUTHORIZED);
        }

        return gson.fromJson(jsonToken, TokenAO.class);
    }

}
