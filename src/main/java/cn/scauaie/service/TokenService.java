package cn.scauaie.service;

import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.service.constant.TokenType;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
public interface TokenService {

    String createAndSaveFormToken(String code, Integer formId, String dep);

    TokenAO authTokenAndSetExpire(HttpServletRequest request);

    TokenAO authTokenAndSetExpire(HttpServletRequest request, TokenType tokenType);

}
