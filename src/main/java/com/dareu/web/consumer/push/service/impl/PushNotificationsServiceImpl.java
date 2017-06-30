package com.dareu.web.consumer.push.service.impl;

import com.dareu.web.consumer.push.service.PushNotificationsService;
import com.dareu.web.dto.jms.PayloadMessage;
import de.bytefish.fcmjava.client.FcmClient;
import de.bytefish.fcmjava.model.enums.PriorityEnum;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.requests.data.DataUnicastMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component(value = "pushNotificationsService")
public class PushNotificationsServiceImpl implements PushNotificationsService {

    @Autowired
    @Qualifier("fcmClient")
    private FcmClient fcmClient;

    private final Logger logger = Logger.getLogger(getClass());

    private static final FcmMessageOptions options = FcmMessageOptions.builder()
            .setTimeToLive(Duration.ofHours(12))
            .setDelayWhileIdle(true)
            .setPriorityEnum(PriorityEnum.High)
            .build();

    public void send(String token, PayloadMessage pushPayloadMessage){
        if(token != null){
            fcmClient.send(new DataUnicastMessage(options, token, pushPayloadMessage));
        }else{
            logger.info("No FCM token provided to send push notification");
        }
    }
}
