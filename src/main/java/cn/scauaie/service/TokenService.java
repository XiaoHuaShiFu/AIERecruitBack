package cn.scauaie.service;

import cn.scauaie.model.ao.TokenAO;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 21:30
 */
public interface TokenService {
    TokenAO authFormToken(String token);

    String createAndSaveFormToken(String code, Integer formId);
}
