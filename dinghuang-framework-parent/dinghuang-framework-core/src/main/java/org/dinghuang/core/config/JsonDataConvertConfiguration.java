package org.dinghuang.core.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigInteger;
import java.util.List;

/**
 * 处理Long类型超过17位被浏览器自动截断的问题
 *
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
@EnableWebMvc
@Configuration
public class JsonDataConvertConfiguration implements WebMvcConfigurer {

    /**
     * 重写WebMvcConfigurerAdapter的configureMessageConverters抽象方法   
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 初始化转换器
        FastJsonHttpMessageConverter fastConvert = new FastJsonHttpMessageConverter();
        // 初始化一个转换器配置
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.BrowserCompatible,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.DisableCircularReferenceDetect);
        //解决Long转json精度丢失的问题
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        fastJsonConfig.setSerializeConfig(serializeConfig);
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
}
