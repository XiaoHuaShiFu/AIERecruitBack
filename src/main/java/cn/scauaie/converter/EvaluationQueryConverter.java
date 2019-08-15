package cn.scauaie.converter;

import cn.scauaie.model.query.EvaluationQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 描述: 评价表查询参数校验器
 *  必须符合是 [是否通过、报名表编号] 中的一个
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 13:57
 */
@Component("evaluationQueryConverter")
public class EvaluationQueryConverter implements Converter<String, EvaluationQuery> {

    private final static String PASS = "通过";
    private final static String NOT_PASS = "未通过";

    @Override
    public EvaluationQuery convert(String source) {
        if (StringUtils.isBlank(source)) {
            return new EvaluationQuery();
        }
        if (NumberUtils.isDigits(source)) {
            EvaluationQuery evaluationQuery = new EvaluationQuery();
            evaluationQuery.setFid(Integer.valueOf(source));
            return evaluationQuery;
        }
        if (source.equals(PASS)) {
            EvaluationQuery evaluationQuery = new EvaluationQuery();
            evaluationQuery.setPass(true);
            return evaluationQuery;
        }
        if (source.equals(NOT_PASS)) {
            EvaluationQuery evaluationQuery = new EvaluationQuery();
            evaluationQuery.setPass(false);
            return evaluationQuery;
        }
        return new EvaluationQuery();
    }

}
