package eu.codeacademy.baigiamasis.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

public class MessageConfigs {
    @Bean
    public ResourceBundleMessageSource messageSource(){
        var source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }
}
