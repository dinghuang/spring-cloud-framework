package org.dinghuang.core.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 处理Long类型超过17位被浏览器自动截断的问题
 *
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
@Configuration
public class JsonDataConvertConfiguration implements WebMvcConfigurer {

    private static final Long MAX_SIZE = 10000000000000000L;

    /**
     * favorPathExtension表示支持后缀匹配，
     * <p>
     * 属性ignoreAcceptHeader默认为fasle，表示accept-header匹配，defaultContentType开启默认匹配。
     * <p>
     * 例如：请求aaa.xx，若设置<entry key="xx" value="application/xml"/> 也能匹配以xml返回。
     * <p>
     * 根据以上条件进行一一匹配最终，得到相关并符合的策略初始化ContentNegotiationManager
     *
     * @param configurer configurer
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

    /**
     * 重写WebMvcConfigurerAdapter的configureMessageConverters抽象方法   
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 初始化转换器
        FastJsonHttpMessageConverter fastConvert = new FastJsonHttpMessageConverter();
        // 初始化一个转换器配置
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                //开启浏览器兼容，可能会导致浏览器中文被编码
//                SerializerFeature.BrowserCompatible,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNonStringKeyAsString,
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.SortField);
        //解决中文乱码
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        //解决Long转json精度丢失的问题
        fastJsonConfig.setSerializeFilters(new ToStringSerializer());
        // 将配置设置给转换器并添加到HttpMessageConverter转换器列表中
        fastConvert.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConvert);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    public class ToStringSerializer implements ValueFilter {

        @Override
        @SuppressWarnings("unchecked")
        public Object process(Object object, String name, Object value) {
            if (value != null) {
                if (value instanceof Long) {
                    if ((Long) value >= MAX_SIZE) {
                        value = value + "";
                    }
                    return value;
                } else if (value instanceof List) {
                    return ((List) value).stream().map(k -> {
                        if (k instanceof Long) {
                            if ((Long) k >= MAX_SIZE) {
                                return k + "";
                            } else {
                                return k;
                            }
                        } else {
                            return k;
                        }
                    }).collect(Collectors.toList());
                } else {
                    return value;
                }
            } else {
                return null;
            }
        }
    }
}