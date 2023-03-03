package com.antonromanov.arnote;

import com.antonromanov.arnote.common.wrapper.ResponseWrapperAdvice;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@AllArgsConstructor
public class ResponseWrapperAutoConfiguration { // todo: в пекедж конфигов

    @Bean
    @ConditionalOnMissingBean
    public ResponseWrapperAdvice responseWrapperAdvice() {
        return new ResponseWrapperAdvice();
    }
}
