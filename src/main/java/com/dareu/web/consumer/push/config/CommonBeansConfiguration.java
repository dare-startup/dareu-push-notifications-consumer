package com.dareu.web.consumer.push.config;

import de.bytefish.fcmjava.client.FcmClient;
import de.bytefish.fcmjava.client.settings.PropertiesBasedSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;
import java.nio.file.Paths;

@Configuration
@ComponentScan("com.dareu.web.consumer.push")
public class CommonBeansConfiguration {

    //@Value("#{}") TODO: MUST CHANGE TO THIS
    private final String propertiesFile = System.getProperty("com.dareu.web.message.properties");

    @Bean
    @Qualifier("fcmClient")
    public FcmClient fcmClient(){
        PropertiesBasedSettings settings = PropertiesBasedSettings.createFromFile(Paths.get(propertiesFile), Charset.forName("UTF-8"));
        return new FcmClient(settings);
    }

    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer(){
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        return configurer;
    }
}
