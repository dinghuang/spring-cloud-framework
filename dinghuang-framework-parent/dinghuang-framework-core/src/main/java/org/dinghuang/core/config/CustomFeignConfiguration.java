package org.dinghuang.core.config;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.dinghuang.core.client.CustomFeignClientAdaptor;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/15
 */
@Configuration
@Import(FeignClientsConfiguration.class)
public class CustomFeignConfiguration {

    @Bean(name = "customFeignClientAdaptor")
    public CustomFeignClientAdaptor instanceServiceImpl(Decoder decoder, Encoder encoder, Client client, Contract contract) {
        return Feign.builder().encoder(encoder).decoder(decoder)
                .client(client)
                .contract(new Contract.Default())
                .target(Target.EmptyTarget.create(CustomFeignClientAdaptor.class));
    }

}
