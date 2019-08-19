package cn.scauaie.service;

import cn.scauaie.constant.TokenType;
import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.result.Result;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
public interface TokenService {

    Result<TokenAO> saveToken(TokenAO tokenAO);

    Result<TokenAO> saveToken(TokenAO tokenAO, int seconds);

    Result<TokenAO> createAndSaveToken(String tokenType, String code);

    Result<TokenAO> getToken(String token);

    Result<TokenAO> getTokenAndAuthTokenTypeAndUpdateExpire(String token, TokenType[] tokenTypes, int seconds);

    @Deprecated
    String createAndSaveFormToken(String code, Integer formId, String dep);

    @Deprecated
    String createAndSaveInterviewerToken(String code, Integer interviewerId, String dep);

}
