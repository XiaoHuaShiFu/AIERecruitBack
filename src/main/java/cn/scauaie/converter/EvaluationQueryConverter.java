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
    private final static String DELIMITER = " ";

    @Override
    public EvaluationQuery convert(String source) {
        if (StringUtils.isBlank(source)) {
            return new EvaluationQuery();
        }

        String[] keyWords = source.split(DELIMITER);
        EvaluationQuery evaluationQuery = new EvaluationQuery();
        for (String keyWord : keyWords) {
            if (NumberUtils.isDigits(keyWord)) {
                evaluationQuery.setFid(Integer.valueOf(keyWord));
            } else if (keyWord.equals(PASS)) {
                evaluationQuery.setPass(true);
            } else if (keyWord.equals(NOT_PASS)) {
                evaluationQuery.setPass(false);
            } else {
                evaluationQuery.setName(keyWord);
            }
        }

        return evaluationQuery;
    }

}
