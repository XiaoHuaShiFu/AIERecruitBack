package cn.scauaie.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

/**
 * 描述: 简单封装 Properties 相关方法
 *
 * @author XHSF
 * @email 827032783@qq.com
 * @create 2019-08-08 21:31
 */
public class PropertiesUtils {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    private final String propertiesName;

    /**
     * 初始化properties的文件名
     * @param fileName String
     */
    public PropertiesUtils(String fileName) {
        this.propertiesName = fileName;
    }

    /**
     * 获取对应key的value
     * @param key String
     * @return String
     */
    public String getProperty(String key){
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Properties properties = new Properties();
        String value = null;
        try (InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(PropertiesUtils.class.getClassLoader().getResourceAsStream(propertiesName)), StandardCharsets.UTF_8)) {
            properties.load(inputStreamReader);
            value = properties.getProperty(key.trim());
        } catch (IOException e) {
            logger.error("Get property error.", e);
        }
        return value;
    }

    /**
     * 获取对应key的value，如果value为null，则返回defaultValue
     * @param key String
     * @param defaultValue String
     * @return String
     */
    public String getProperty(String key, String defaultValue){
        String value = getProperty(key);
        return StringUtils.isBlank(value) ? defaultValue : value;
    }

}
