package cn.scauaie.service;

import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.model.result.Result;
import cn.scauaie.constant.TokenType;

import javax.servlet.http.HttpServletRequest;

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

    @Deprecated
    String createAndSaveFormToken(String code, Integer formId, String dep);

    @Deprecated
    String createAndSaveInterviewerToken(String code, Integer interviewerId, String dep);

    Result<TokenAO> getToken(String token);

    Result<TokenAO> getToken(HttpServletRequest request);

//    Result<TokenAO> authTokenAndSetExpire(HttpServletRequest request);

    Result<TokenAO> authTokenAndSetExpire(HttpServletRequest request, TokenType tokenType);

    Result<TokenAO> getTokenAndAuthTokenTypeAndUpdateExpire(String token, TokenType[] tokenTypes, int seconds);
}
