package cn.scauaie.validator.annotation;


import cn.scauaie.validator.DepValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: 部门校验
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 15:12
 */
@Documented
@Constraint(validatedBy = {DepValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Dep.List.class)
public @interface Dep {

    String message() default "INVALID_PARAMETER: The value of dep must be [bgs|chb|cwb|jsb|skb|wlb|xcb|xmb|xwb|yyb|zkb].";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Dep[] value();
    }

}
