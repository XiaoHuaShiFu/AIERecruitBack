package cn.scauaie.service.impl;

import cn.scauaie.cache.RedisHash;
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
    private RedisHash redisHash;

    /**
     * 在缓存里添加from-token
     * @param id 报名表id
     * @return token
     */
    public String createFormToken(Integer id) {
        String token = SHA256.encryption(UUID.randomUUID().toString());
        Long rowCount = redisHash.set("form:token", token, String.valueOf(id));
        if (rowCount < 1) {
            throw new ProcessingException(ErrorCode.INTERNAL_ERROR, "Failed to create form-token.");
        }
        return token;
    }

}
