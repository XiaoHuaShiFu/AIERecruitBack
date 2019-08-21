package cn.scauaie.config;

import cn.scauaie.util.PropertiesUtils;
import cn.scauaie.util.ftp.FTPClientTemplate;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 一些类库的单例
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 19:13
 */
@Configuration
public class SingletonBeansConfig {

    /**
     * Gson单例
     * @return Gson
     */
    @Bean
    public Gson gson() {
        return new Gson();
    }

    /**
     * RestTemplate单例
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        List<MediaType> mediaTypes = new ArrayList<>();
        //加入text/plain类型的支持
        mediaTypes.add(MediaType.TEXT_PLAIN);
        //加入text/html类型的支持
        mediaTypes.add(MediaType.TEXT_HTML);
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setSupportedMediaTypes(mediaTypes);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(messageConverter);
        return restTemplate;
    }

    /**
     * FTPClientTemplate单例
     * @return FTPClientTemplate
     */
    @Bean
    public FTPClientTemplate ftpClientTemplate() {
        String host = PropertiesUtils.getProperty("ftp.host", "ftp.properties");
        String username = PropertiesUtils.getProperty("ftp.username", "ftp.properties");
        String password = PropertiesUtils.getProperty("ftp.password", "ftp.properties");
        return new FTPClientTemplate(host, username, password);
    }

    /**
     * dozer配置
     *
     * @return Mapper
     */
    @Bean
    public Mapper mapper() {
        return DozerBeanMapperBuilder.buildDefault();
    }

}
