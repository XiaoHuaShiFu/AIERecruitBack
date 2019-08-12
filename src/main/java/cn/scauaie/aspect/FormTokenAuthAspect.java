package cn.scauaie.aspect;

import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.service.CacheService;
import cn.scauaie.service.TokenService;
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
    private TokenService tokenService;

    /**
     * 获取form-token令牌
     *
     * @param joinPoint ProceedingJoinPoint
     * @param request HttpServletRequest
     * @return Object
     * 会把fid设置在request.attribute.fid里
     *
     * @throws Throwable .
     *
     * @errors:
     * UNAUTHORIZED
     * UNAUTHORIZED_TOKEN_IS_NULL
     * FORBIDDEN_SUB_USER
     *
     */
    @Around(value = "@annotation(cn.scauaie.aspect.annotation.FormTokenAuth) && args(request, ..)")
    public Object authToken(ProceedingJoinPoint joinPoint, HttpServletRequest request) throws Throwable {
        String token = request.getHeader("authorization");
        TokenAO tokenAO = tokenService.authFormToken(token);

        //把此用户的fid传递给控制器
        request.setAttribute("fid", tokenAO.getValue().getId());
        return joinPoint.proceed();
    }

}
