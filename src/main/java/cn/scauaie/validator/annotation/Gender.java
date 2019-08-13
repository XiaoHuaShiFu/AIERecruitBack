package cn.scauaie.validator.annotation;


import cn.scauaie.validator.GenderValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: 性别校验
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 13:52
 */
@Documented
@Constraint(validatedBy = {GenderValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Gender.List.class)
public @interface Gender {

    String message() default "INVALID_PARAMETER: The name of gender must be [N|M|W].";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Gender[] value();
    }

}
