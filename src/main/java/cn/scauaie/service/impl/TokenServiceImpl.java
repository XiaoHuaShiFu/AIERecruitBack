package cn.scauaie.service.impl;

import cn.scauaie.constant.TokenExpire;
import cn.scauaie.constant.TokenType;
import cn.scauaie.result.ErrorCode;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.InterviewerAO;
import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.result.Result;
import cn.scauaie.service.CacheService;
import cn.scauaie.service.FormService;
import cn.scauaie.service.InterviewerService;
import cn.scauaie.service.TokenService;
import cn.scauaie.service.constant.RedisStatus;
import cn.scauaie.util.SHA256;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private CacheService cacheService;

    @Autowired
    private FormService formService;

    @Autowired
    private InterviewerService interviewerService;

    @Autowired
    private Gson gson;

    /**
     * 在缓存里添加token，并设置过期时间
     *
     * @param tokenAO TokenAO
     * @return Result<TokenAO>
     */
    @Override
    public Result<TokenAO> saveToken(TokenAO tokenAO) {
        return saveToken(tokenAO, TokenExpire.NORMAL.getExpire());
    }

    /**
     * 在缓存里添加token，并设置过期时间
     *
     * @param tokenAO TokenAO
     * @param seconds 过期时间
     * @return Result<TokenAO>
     */
    @Override
    public Result<TokenAO> saveToken(TokenAO tokenAO, int seconds) {
        //保存token
        String code = cacheService.set(tokenAO.getToken(), gson.toJson(tokenAO));

        //保存失败
        if (!code.equals(RedisStatus.OK.name())) {
            logger.error("Failed to create token, type: {} and id: {}", tokenAO.getToken(), tokenAO.getId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Failed to create token.");
        }

        //设置过期时间
        Long result = cacheService.expire(tokenAO.getToken(), TokenExpire.NORMAL.getExpire());
        if (result.equals(0L)) {
            cacheService.del(tokenAO.getToken());
            logger.error("Failed to set expire, type: {} and id: {}", tokenAO.getToken(), tokenAO.getId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Failed to set expire.");
        }

        return Result.success(tokenAO);
    }

    /**
     * 创建token并保存到redis里
     *
     * @param tokenType token类型
     * @param code wx.login()接口获取的返回值
     * @return Result<TokenAO>
     */
    @Override
    public Result<TokenAO> createAndSaveToken(String tokenType, String code) {
        TokenAO tokenAO;
        if (tokenType.equals(TokenType.INTERVIEWER.name())) {
            InterviewerAO interviewer = interviewerService.getInterviewerByCode(code);
            tokenAO = new TokenAO();
            tokenAO.setId(interviewer.getId());
            tokenAO.setDep(interviewer.getDep());
            tokenAO.setType(TokenType.INTERVIEWER.name());
        } else {
            FormAO form = formService.getFormByCode(code);
            tokenAO = new TokenAO();
            tokenAO.setId(form.getId());
            tokenAO.setDep(form.getFirstDep());
            tokenAO.setType(TokenType.FORM.name());
        }

        String token = createToken();
        tokenAO.setToken(token);

        return saveToken(tokenAO);
    }

    /**
     * 获取token
     *
     * @param token token
     * @return Result<TokenAO>
     */
    @Override
    public Result<TokenAO> getToken(String token) {
        String jsonToken = cacheService.get(token);
        //token不在
        if (jsonToken == null) {
            return Result.fail(ErrorCode.UNAUTHORIZED);
        }

        return Result.success(gson.fromJson(jsonToken, TokenAO.class));
    }

    /**
     * 获取token并认证是不是可以通过认证的类型并更新过期时间
     *
     * @param token 要认证类型
     * @param tokenTypes 可以通过认证的列表
     * @param seconds 过期时间长度
     *
     * @return Result<TokenAO>
     */
    @Override
    public Result<TokenAO> getTokenAndAuthTokenTypeAndUpdateExpire(String token, TokenType[] tokenTypes, int seconds) {
        Result<TokenAO> result = getToken(token);
        if (!result.isSuccess()) {
            return Result.fail(result.getErrorCode(), result.getMessage());
        }

        //不是所需的token类型
        TokenAO tokenAO = result.getData();
        if (!authTokenType(tokenAO.getType(), tokenTypes)) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        //更新token过期时间
        Long expire = cacheService.expire(result.getData().getToken(), seconds);
        if (expire == 0) {
            logger.warn("Set redis expire fail, token: {} id: {} type: {}",
                    tokenAO.getToken(), tokenAO.getId(), tokenAO.getType());
        }

        return result;
    }

    /**
     * 创建并保存Form-token
     *
     * @param code   wx.login()接口获取的返回值
     * @param formId 报名表编号
     * @param dep    部门
     * @return token
     */
    @Override
    @Deprecated
    public String createAndSaveFormToken(String code, Integer formId, String dep) {
        String token = createToken();
        //装配成TokenAO
        TokenAO tokenAO = new TokenAO(token, TokenType.FORM.name(), formId, dep);

        //保存form-token
        saveToken(tokenAO);

        return token;
    }

    /**
     * 创建并保存Interviewer-token
     *
     * @param code          wx.login()接口获取的返回值
     * @param interviewerId 面试官编号
     * @param dep           部门
     * @return token
     */
    @Override
    @Deprecated
    public String createAndSaveInterviewerToken(String code, Integer interviewerId, String dep) {
        String token = createToken();
        //装配成TokenAO
        TokenAO tokenAO = new TokenAO(token, TokenType.INTERVIEWER.name(), interviewerId, dep);

        //保存form-token
        saveToken(tokenAO);

        return token;
    }

    /**
     * 认证tokenType
     *
     * @param type 要认证类型
     * @param tokenTypes 可以通过认证的列表
     * @return 是否认证成功
     */
    private boolean authTokenType(String type, TokenType[] tokenTypes) {
        if (tokenTypes.length == 0) {
            return true;
        }
        for (TokenType tokenType : tokenTypes) {
            if (tokenType.name().equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建token
     *
     * @return token
     */
    private String createToken() {
        return SHA256.encryption(UUID.randomUUID().toString());
    }

}
