package cn.scauaie.util;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述: Bean类型的类的检查器
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-23 1:26
 */
public class BeanUtils {

    private final static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    private Mapper mapper;

    public BeanUtils() {
        mapper = DozerBeanMapperBuilder.buildDefault();
    }

    public BeanUtils(Mapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 判断类中每个属性是否都为空
     *
     * @param bean bean类
     * @return 是否全为空
     */
    public boolean allFieldIsNull(Object bean) {
        try {
            for (Field field : bean.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(bean);
                if (null != value) {
                    return false;
                }
            }
        } catch (IllegalAccessException e) {
            logger.error("Check all field is null error.", e);
            return false;
        }
        return true;
    }

    /**
     * 将bean列表转换成另外一种类型的bean列表
     *
     * @param source 原列表
     * @param clazz  目标列表类型
     * @param <T>    类型
     * @return 目标列表
     */
    public <T> List<T> mapList(List<?> source, Class<T> clazz) {
        List<T> list = new ArrayList<>(source.size());
        mapper.map(source, list);
        return list;
    }

}
