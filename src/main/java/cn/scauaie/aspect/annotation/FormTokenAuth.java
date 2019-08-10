package cn.scauaie.aspect.annotation;

import java.lang.annotation.*;

/**
 * 描述: 进行Form-token身份认证
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-06 22:29
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormTokenAuth {
}
