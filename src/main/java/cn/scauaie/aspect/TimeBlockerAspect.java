package cn.scauaie.aspect;

import cn.scauaie.aspect.annotation.TimeBlocker;
import cn.scauaie.exception.ProcessingException;
import cn.scauaie.result.ErrorCode;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Before(value = "@annotation(cn.scauaie.aspect.annotation.TimeBlocker) && @annotation(timeBlocker)")
    public void handler(TimeBlocker timeBlocker) {
        LocalDateTime until = LocalDateTime.parse(timeBlocker.dateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
       if (LocalDateTime.now().isBefore(until)) {
           throw new ProcessingException(ErrorCode.FORBIDDEN, "Operation is not allowed at current time.");
       }
    }

}
