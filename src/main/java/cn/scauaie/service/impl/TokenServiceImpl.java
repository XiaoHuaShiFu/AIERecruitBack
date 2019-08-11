package cn.scauaie.service.impl;

import cn.scauaie.cache.Redis;
import cn.scauaie.common.error.ErrorCode;
import cn.scauaie.common.redis.RedisStatus;
import cn.scauaie.common.token.TokenExpire;
import cn.scauaie.common.token.TokenType;
import cn.scauaie.error.ProcessingException;
import cn.scauaie.model.dao.TokenDO;
import cn.scauaie.service.TokenService;
import cn.scauaie.utils.SHA256;
import com.google.gson.Gson;
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

    @Autowired
    private Redis redis;

    @Autowired
    private Gson gson;

    /**
     * 在缓存里添加from-token
     * @param id 报名表id
     * @return token
     */
    @Override
    public String createFormToken(Integer id) {
        //装配成TokenDO
        TokenDO tokenDO = new TokenDO(TokenType.FORM.getType(), id);
        //生成token
        String token = SHA256.encryption(UUID.randomUUID().toString());
        //保存form-token
        String code = redis.set(token, gson.toJson(tokenDO));
        //如果保存失败
        if (!code.equals(RedisStatus.OK.name())) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Failed to create form-token.");
        }

        //设置过期时间
        redis.expire(token, TokenExpire.NORMAL.getExpire());
        return token;
    }

}
