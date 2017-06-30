package com.dareu.web.consumer.push.service;

import com.dareu.web.dto.jms.PayloadMessage;

public interface PushNotificationsService {
    public void send(String token, PayloadMessage pushPayloadMessage);
}
