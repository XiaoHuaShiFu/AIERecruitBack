package cn.scauaie.aspect;

import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.service.TokenService;
import cn.scauaie.service.constant.TokenType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述: 身份认证切面
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 22:22
 */
@Aspect
@Component
public class TokenAuthAspect {

    @Autowired
    private TokenService tokenService;

    // FIXME: 2019/8/14 这里可以再封装
    /**
     * 认证form-token令牌
     *
     * @param joinPoint ProceedingJoinPoint
     * @param request HttpServletRequest
     * @return Object
     * 会把TokenAO设置在request.attribute.tokenAO里
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
    public Object authFormToken(ProceedingJoinPoint joinPoint, HttpServletRequest request) throws Throwable {
        TokenAO tokenAO = tokenService.authTokenAndSetExpire(request, TokenType.FORM);

        //把tokenAO传递给控制器
        request.setAttribute("tokenAO", tokenAO);
        return joinPoint.proceed();
    }

    /**
     * 认证interviewer-token令牌
     *
     * @param joinPoint ProceedingJoinPoint
     * @param request HttpServletRequest
     * @return Object
     * 会把TokenAO设置在request.attribute.tokenAO里
     *
     * @throws Throwable .
     *
     * @errors:
     * UNAUTHORIZED
     * UNAUTHORIZED_TOKEN_IS_NULL
     * FORBIDDEN_SUB_USER
     *
     */
    @Around(value = "@annotation(cn.scauaie.aspect.annotation.InterviewerTokenAuth) && args(request, ..)")
    public Object authInterviewerToken(ProceedingJoinPoint joinPoint, HttpServletRequest request) throws Throwable {
        TokenAO tokenAO = tokenService.authTokenAndSetExpire(request, TokenType.INTERVIEWER);

        //把tokenAO传递给控制器
        request.setAttribute("tokenAO", tokenAO);
        return joinPoint.proceed();
    }

    /**
     * 认证token令牌
     *
     * @param joinPoint ProceedingJoinPoint
     * @param request HttpServletRequest
     * @return Object
     * 会把TokenAO设置在request.attribute.tokenAO里
     *
     * @throws Throwable .
     *
     * @errors:
     * UNAUTHORIZED
     * UNAUTHORIZED_TOKEN_IS_NULL
     * FORBIDDEN_SUB_USER
     *
     */
    @Around(value = "@annotation(cn.scauaie.aspect.annotation.TokenAuth) && args(request, ..)")
    public Object authToken(ProceedingJoinPoint joinPoint, HttpServletRequest request) throws Throwable {
        TokenAO tokenAO = tokenService.authTokenAndSetExpire(request);

        //把此id传递给控制器
        request.setAttribute("tokenAO", tokenAO);
        return joinPoint.proceed();
    }

}
