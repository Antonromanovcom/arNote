package com;

import com.antonromanov.arnote.bot.Bot;
import com.antonromanov.arnote.services.investment.calc.shares.foreign.ForeignCalcServiceImpl;
import com.antonromanov.arnote.services.investment.calc.shares.moex.MoexCalculateServiceImpl;
import com.antonromanov.arnote.services.investment.calc.shares.common.CalculateFactory;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.concurrent.TimeUnit;



@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableCaching
public class Application {


    public static void main(String[] args) {

       /* try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/

        SpringApplication.run(Application.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    RestTemplate SimpleRestTemplate() {
        return new RestTemplate();
    }

    @Bean("calcFactory")
    public FactoryBean serviceLocatorFactoryBean() {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(CalculateFactory.class);
        return factoryBean;
    }

    @Bean(name = "MOEX")
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public MoexCalculateServiceImpl moexCalculator() {
        return new MoexCalculateServiceImpl();
    }

    @Bean(name = "SPB")
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ForeignCalcServiceImpl foreignCalculator() {
        return new ForeignCalcServiceImpl();
    }


    @Bean("habrCacheManager")
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(String name) {
                return new ConcurrentMapCache(
                        name,
                        CacheBuilder.newBuilder()
                                .expireAfterWrite(15, TimeUnit.MINUTES)
                                .build().asMap(),
                        false);/*@Bean
    @Autowired
    Bot bot(Environment env) {
        Bot bot = null;
     //   if(env.getProperty("telegram.bot", Boolean.TYPE)) {
            TelegramBotsApi botsApi = new TelegramBotsApi();
            try {
                DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
              //  if (env.getProperty("telegram.proxy.set", Boolean.TYPE)) {

                  *//*  Authenticator.setDefault(new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(env.getProperty("telegram.proxy.user"), env.getProperty("telegram.proxy.pass").toCharArray());
                        }
                    });

                    botOptions.setProxyHost(env.getProperty("telegram.proxy.host"));
                    botOptions.setProxyPort(env.getProperty("telegram.proxy.port", Integer.TYPE));
                    botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);*//*
              //  }
                botsApi.registerBot(bot = new Bot(botOptions));
            } catch ( TelegramApiException e) {
                e.printStackTrace();
            }
       // }
        return bot;
    }*/
            }
        };
    }




}
