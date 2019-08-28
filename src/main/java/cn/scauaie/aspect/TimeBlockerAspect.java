package cn.scauaie.aspect;

import cn.scauaie.exception.ProcessingException;
import cn.scauaie.result.ErrorCode;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 描述: 对各个接口的阻拦操作，需要在规定时间之后才可以调用被此切面切入的接口
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-28 17:52
 */
@Aspect
@Component
@Order(1)
public class TimeBlockerAspect {

    /**
     * 阻塞直到
     */
    private static final LocalDateTime BLOCKING_UNTIL = LocalDateTime.of(
            2019, 9, 7, 8, 0, 0);

    @Before(value = "@annotation(cn.scauaie.aspect.annotation.TimeBlocker)")
    public void handler() {
       if (LocalDateTime.now().isBefore(BLOCKING_UNTIL)) {
           throw new ProcessingException(ErrorCode.FORBIDDEN, "Operation is not allowed at current time.");
       }
    }

}
