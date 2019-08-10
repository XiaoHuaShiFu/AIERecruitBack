package cn.scauaie.service.impl;

import cn.scauaie.cache.RedisString;
import cn.scauaie.common.error.ErrorCode;
import cn.scauaie.error.ProcessingException;
import cn.scauaie.service.TokenService;
import cn.scauaie.utils.SHA256;
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
    private RedisString redisString;

    /**
     * 在缓存里添加from-token
     * @param id 报名表id
     * @return token
     */
    public String createFormToken(Integer id) {
        String token = SHA256.encryption(UUID.randomUUID().toString());
        String code = redisString.set(token, String.valueOf(id));
        if (!code.equals("OK")) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Failed to create form-token.");
        }
        redisString.expire(token, 1800);
        return token;
    }

}
