package com.dareu.web.consumer.push.listener;
import com.amazon.sqs.javamessaging.message.SQSTextMessage;
import com.dareu.web.consumer.push.service.PushNotificationsService;
import com.dareu.web.dto.jms.QueueMessage;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
public class PushNotificationMessageListener {

    @Autowired
    private PushNotificationsService pushNotificationsService;

    private final Logger logger = Logger.getLogger(getClass());

    public void onMessage(Message message){
        if(message instanceof SQSTextMessage){
            SQSTextMessage sqsMessage = (SQSTextMessage)message;
            try{
                final String payload = sqsMessage.getText();
                QueueMessage queueMessage = new Gson().fromJson(payload, QueueMessage.class);
                pushNotificationsService.send(queueMessage.getToken(), queueMessage.getPayloadMessage());
            }catch(JMSException ex){
                //TODO: send message to errors_queue
                logger.error(ex.getMessage());
            }catch(Exception ex){
                //TODO: send message to error queue with different payload
                logger.error(ex.getMessage());
            }
        }else{
            logger.info("Got non SQSTextMessage message: " + message);
        }
    }
}
