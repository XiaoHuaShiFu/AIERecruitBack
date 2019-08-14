package cn.scauaie.converter;

import cn.scauaie.constant.DepEnum;
import cn.scauaie.constant.GenderEnum;
import cn.scauaie.model.query.FormQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 描述: 部门查询参数校验器
 *  必须符合是 [部门（用大写缩写）、报名表编号、性别、名字] 中的一个
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 13:57
 */
@Component("formQueryConverter")
public class FormQueryConverter implements Converter<String, FormQuery> {

    @Override
    public FormQuery convert(String source) {
        if (StringUtils.isBlank(source)) {
            return new FormQuery();
        }
        if (NumberUtils.isDigits(source)) {
            FormQuery formQuery = new FormQuery();
            formQuery.setId(Integer.valueOf(source));
            return formQuery;
        }
        for (DepEnum dep : DepEnum.values()) {
            if (source.equals(dep.name())) {
                FormQuery formQuery = new FormQuery();
                formQuery.setDep(source);
                return formQuery;
            }
        }
        for (GenderEnum gender : GenderEnum.values()) {
            if (source.equals(gender.name())) {
                FormQuery formQuery = new FormQuery();
                formQuery.setGender(source);
                return formQuery;
            }
        }
        FormQuery formQuery = new FormQuery();
        formQuery.setName(source);
        return formQuery;
    }
}
