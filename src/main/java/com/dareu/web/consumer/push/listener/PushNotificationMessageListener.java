package com.dareu.web.consumer.push.listener;
import com.amazon.sqs.javamessaging.message.SQSTextMessage;
import com.dareu.web.consumer.push.service.PushNotificationsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(value = "pushNotificationMessageListener")
public class PushNotificationMessageListener {

    @Autowired
    @Qualifier("pushNotificationsService")
    private PushNotificationsService pushNotificationsService;

    private final Logger logger = Logger.getLogger(getClass());

    public void onMessage(SQSTextMessage message){
        try{
            pushNotificationsService.send(message);
        } catch(Exception ex){
            logger.error(ex.getMessage());
        }
    }
}
