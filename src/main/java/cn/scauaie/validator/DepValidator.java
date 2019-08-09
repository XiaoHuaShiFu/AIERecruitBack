package cn.scauaie.validator;

import cn.scauaie.validator.annotation.Dep;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: 部门校验器
 *  必须符合是 [bgs, chb, cwb, jsb, skb, wlb, xcb, xmb, xwb, yyb, zkb] 中的一个
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 13:57
 */
public class DepValidator implements ConstraintValidator<Dep, String> {

    /**
     * 部门代号列表
     */
    private static final String[] ARRAY_DEP =
            {"bgs", "chb", "cwb", "jsb", "skb", "wlb", "xcb", "xmb", "xwb", "yyb", "zkb"};

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        for (String dep : ARRAY_DEP) {
            if (dep.equals(s)) {
                return true;
            }
        }
        return false;
    }
}
