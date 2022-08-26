package com.antonromanov.arnote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        log.info("====================================================");
        log.info("Request URL:: " + request.getRequestURL().toString());

        // Get request parameters
        String queryString = request.getQueryString();
        log.info("MyInterceptor Request parameters :{}", queryString);

        byte[] bodyBytes = new byte[0];

   //     try {
            /*bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());

            String body = new String(bodyBytes, request.getCharacterEncoding());
            log.info("MyInterceptor Request body ：{}", body);*/
      //  } catch (IOException e) {
            log.info("GAVNO ：{}");
    //    }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("НИ ХУЯ СЕБЕ!!!!!");
    }
}
