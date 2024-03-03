package ru.loggable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.loggable.aspects.LoggableAspect;

@Configuration
public class LoggableStarterConfig {

    @Bean
    public LoggableAspect loggableAspect(){
        return new LoggableAspect();
    }
}
