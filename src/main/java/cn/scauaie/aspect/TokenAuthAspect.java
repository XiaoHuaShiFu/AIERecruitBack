package cn.scauaie.aspect;

import cn.scauaie.aspect.annotation.TokenAuth;
import cn.scauaie.error.ErrorCode;
import cn.scauaie.error.ErrorResponse;
import cn.scauaie.model.ao.TokenAO;
import cn.scauaie.model.result.Result;
import cn.scauaie.service.TokenService;
import cn.scauaie.constant.TokenType;
import cn.scauaie.service.constant.TokenExpire;
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
        Result<TokenAO> result = tokenService.authTokenAndSetExpire(request, TokenType.FORM);
        if (!result.isSuccess()) {
            return new ErrorResponse(result.getErrorCode(), result.getMessage());
        }

        //把tokenAO传递给控制器
        request.setAttribute("tokenAO", result.getData());
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
        Result<TokenAO> result = tokenService.authTokenAndSetExpire(request, TokenType.INTERVIEWER);
        if (!result.isSuccess()) {
            return new ErrorResponse(result.getErrorCode(), result.getMessage());
        }

        //把tokenAO传递给控制器
        request.setAttribute("tokenAO", result.getData());
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
     */
    @Around(value = "@annotation(cn.scauaie.aspect.annotation.TokenAuth) && @annotation(tokenAuth) && args(request, ..)")
    public Object authToken(ProceedingJoinPoint joinPoint, HttpServletRequest request,
                            TokenAuth tokenAuth) throws Throwable {
        String token = request.getHeader("authorization");
        //token不在头部
        if (token == null) {
            return Result.fail(ErrorCode.UNAUTHORIZED_TOKEN_IS_NULL.getError(), ErrorCode.UNAUTHORIZED_TOKEN_IS_NULL.getMessage());
        }

        TokenType[] tokenTypes = tokenAuth.tokenType();
        Result<TokenAO> result = tokenService.getTokenAndAuthTokenTypeAndUpdateExpire(token, tokenTypes, TokenExpire.NORMAL.getExpire());
        if (!result.isSuccess()) {
            return new ErrorResponse(result.getErrorCode(), result.getMessage());
        }

        //把此id传递给控制器
        request.setAttribute("tokenAO", result.getData());
        return joinPoint.proceed();
    }

}
