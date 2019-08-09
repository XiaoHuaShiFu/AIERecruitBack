package cn.scauaie.aspect;

import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

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

    @Around(value = "@annotation(cn.scauaie.aspect.annotation.FormTokenAuth) && args(session, ..)")
    public Object authToken(ProceedingJoinPoint joinPoint, HttpSession session) throws Throwable {
        if (3 == 3) {
            return new ResponseEntity<>("dddddddddd", HttpStatus.NOT_FOUND);
        }
        return joinPoint.proceed();
    }
}
