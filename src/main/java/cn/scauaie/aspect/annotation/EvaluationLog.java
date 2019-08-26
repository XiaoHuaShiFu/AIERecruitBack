package cn.scauaie.aspect.annotation;

import java.lang.annotation.*;

/**
 * 描述: 评价日志注解
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-26 16:07
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EvaluationLog {
}
