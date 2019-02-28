package org.dinghuang.eureka.listener;

import com.netflix.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
@Component
public class EurekaInstanceRegisteredListener implements ApplicationListener<EurekaInstanceRegisteredEvent> {

    private static final Logger logger = LoggerFactory.getLogger(EurekaInstanceRegisteredListener.class);

    @Override
    public void onApplicationEvent(EurekaInstanceRegisteredEvent eurekaInstanceRegisteredEvent) {
        if (eurekaInstanceRegisteredEvent.isReplication()) {
            try {
                InstanceInfo instanceInfo = eurekaInstanceRegisteredEvent.getInstanceInfo();
                logger.info("a instance up {}", instanceInfo.toString());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

}