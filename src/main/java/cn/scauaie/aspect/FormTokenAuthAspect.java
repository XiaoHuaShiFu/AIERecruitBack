package cn.scauaie.aspect;

import cn.scauaie.cache.Redis;
import cn.scauaie.common.error.ErrorCode;
import cn.scauaie.common.token.TokenExpire;
import cn.scauaie.common.token.TokenType;
import cn.scauaie.error.ProcessingException;
import cn.scauaie.model.dao.TokenDO;
import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 22:22
 */
@Aspect
@Component
public class FormTokenAuthAspect {

    @Autowired
    private Redis redis;

    @Autowired
    private Gson gson;

    /**
     * 获取form-token令牌
     *
     * @param joinPoint ProceedingJoinPoint
     * @param request HttpServletRequest
     * @return Object
     * 会把fid设置在request.attribute.fid里
     *
     *
     * @throws Throwable .
     *
     *
     * @errors:
     * UNAUTHORIZED
     * UNAUTHORIZED_TOKEN_IS_NULL
     * FORBIDDEN_SUB_USER
     *
     */
    @Around(value = "@annotation(cn.scauaie.aspect.annotation.FormTokenAuth) && args(request, ..)")
    public Object authToken(ProceedingJoinPoint joinPoint, HttpServletRequest request) throws Throwable {
        //从Http头获取token
        String token = request.getHeader("authorization");
        //token不存在
        if (token == null) {
            throw new ProcessingException(ErrorCode.UNAUTHORIZED_TOKEN_IS_NULL);
        }

        //获取tokenDO
        String jsonTokenDO = redis.get(token);
        //tokenDO不存在
        if (jsonTokenDO == null) {
            throw new ProcessingException(ErrorCode.UNAUTHORIZED);
        }

        //解析成tokenDO对象
        TokenDO tokenDO = gson.fromJson(jsonTokenDO, TokenDO.class);
        //如果token的类型不是form-token
        if (!tokenDO.getType().equals(TokenType.FORM.getType())) {
            throw new ProcessingException(ErrorCode.FORBIDDEN_SUB_USER);
        }

        //更新token过期时间
        redis.expire(token, TokenExpire.NORMAL.getExpire());
        //把此用户的fid传递给控制器
        request.setAttribute("fid", tokenDO.getId());
        return joinPoint.proceed();
    }




}
