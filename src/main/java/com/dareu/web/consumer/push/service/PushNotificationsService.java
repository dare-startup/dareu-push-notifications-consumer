package com.dareu.web.consumer.push.service;

import com.dareu.web.dto.jms.NotificationMessage;

public interface PushNotificationsService {
    public void send(String token, NotificationMessage pushNotificationMessage);
}
