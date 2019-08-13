package cn.scauaie.validator;

import cn.scauaie.validator.annotation.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: 性别校验器
 *  必须为 N、M、W 中的一个
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 13:57
 */
public class GenderValidator implements ConstraintValidator<Gender, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        return s.equals("N") || s.equals("M") || s.equals("W");
    }
}
