package cn.scauaie.validator;

import cn.scauaie.constant.DepEnum;
import cn.scauaie.validator.annotation.Dep;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: 部门校验器
 *  必须符合是 [BGS, CHB, CWB, JSB, SKB, XCB, XMB, XWB, YYB, ZKB] 中的一个
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 13:57
 */
public class DepValidator implements ConstraintValidator<Dep, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        for (DepEnum dep : DepEnum.values()) {
            if (s.equals(dep.name())) {
                return true;
            }
        }
        return false;
    }
}
