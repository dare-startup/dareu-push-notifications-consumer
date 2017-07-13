package com.dareu.web.consumer.push.service;

import com.amazon.sqs.javamessaging.message.SQSTextMessage;
import com.dareu.web.consumer.push.exception.PushNotificationException;

public interface PushNotificationsService {
    public void send(SQSTextMessage request)throws PushNotificationException;
}
