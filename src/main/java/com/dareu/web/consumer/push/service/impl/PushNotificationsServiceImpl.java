package com.dareu.web.consumer.push.service.impl;

import com.amazon.sqs.javamessaging.message.SQSTextMessage;
import com.dareu.web.consumer.push.exception.PushNotificationException;
import com.dareu.web.consumer.push.service.PushNotificationsService;
import com.google.gson.Gson;
import com.messaging.dto.push.PushNotificationRequest;
import de.bytefish.fcmjava.client.FcmClient;
import de.bytefish.fcmjava.model.enums.PriorityEnum;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.requests.data.DataUnicastMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import java.time.Duration;

@Component(value = "pushNotificationsService")
public class PushNotificationsServiceImpl implements PushNotificationsService {

    @Autowired
    @Qualifier("fcmClient")
    private FcmClient fcmClient;

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    private final Logger logger = Logger.getLogger(getClass());

    private static final FcmMessageOptions options = FcmMessageOptions.builder()
            .setTimeToLive(Duration.ofHours(12))
            .setDelayWhileIdle(true)
            .setPriorityEnum(PriorityEnum.High)
            .build();

    @Override
    public void send(SQSTextMessage message)throws PushNotificationException {
        try{
            final String payload = message.getText();
            if(payload == null || payload.isEmpty())
                throw new PushNotificationException("No payload was provided");
            PushNotificationRequest pushNotificationRequest = gson.fromJson(payload, PushNotificationRequest.class);
            if(pushNotificationRequest.getToken() != null){
                fcmClient.send(new DataUnicastMessage(options, pushNotificationRequest.getToken(), pushNotificationRequest.getPushNotificationPayload()));
            }else
                logger.info("No FCM token provided to send push notification, will skip message and ack");
        }catch(JMSException ex){
            throw new PushNotificationException("Could not read message payload", ex);
        }catch(Exception ex){
            throw new PushNotificationException("Unknown error: " + ex.getMessage(), ex);
        }
    }
}
