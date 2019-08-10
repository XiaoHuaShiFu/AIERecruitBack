package cn.scauaie.aspect;

import cn.scauaie.cache.RedisHash;
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
    private RedisHash redisHash;

    @Around(value = "@annotation(cn.scauaie.aspect.annotation.FormTokenAuth) && args(request, ..)")
    public Object authToken(ProceedingJoinPoint joinPoint, HttpServletRequest request) throws Throwable {
        System.out.println("假装认证");
        request.setAttribute("fid", 233);
        System.out.println(redisHash.get("token", "wjx"));
        System.out.println(request.getAttribute("id"));

        return joinPoint.proceed();
    }
}
