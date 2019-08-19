package cn.scauaie.validator;

import cn.scauaie.validator.annotation.TokenType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: TokenType校验器
 *  必须符合是 [FORM | INTERVIEWER] 中的一个
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 13:57
 */
public class TokenTypeValidator implements ConstraintValidator<TokenType, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        for (cn.scauaie.constant.TokenType
                tokenType : cn.scauaie.constant.TokenType.values()) {
            if (s.equals(tokenType.name())) {
                return true;
            }
        }
        return false;
    }
}
