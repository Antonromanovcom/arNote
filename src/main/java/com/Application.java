package com;

//todo: Почему в пакете com, а не в пакете com.antonromanov.arnote ??? Исправить !!!!!!
import com.antonromanov.arnote.common.logging.LoggerFilter;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableCaching
public class Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext appContext = SpringApplication.run(Application.class, args);
        /*MainService srv = appContext.getBean(MainServiceImpl.class);
        UsersRepo repo = appContext.getBean(UsersRepo.class);
        Environment env = appContext.getBean(Environment.class);

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot(srv, repo, env));
        } catch (TelegramApiException e) {
            e.printStackTrace(); // todo: это наверное как-то исправить надо, да?
        }*/
    }

    @Autowired
    LoggerFilter loggerFilter;



    @Bean
    public FilterRegistrationBean<LoggerFilter> registerLoggerFilter() {
        FilterRegistrationBean<LoggerFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(loggerFilter);
        registrationBean.addUrlPatterns("/rest/wishes/*");
      ///  registrationBean.addUrlPatterns("/activity/*");
     //   registrationBean.addUrlPatterns("/organization/*");
        return registrationBean;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    RestTemplate SimpleRestTemplate() {
        return new RestTemplate();
    }

    /*@Bean("calcFactory")
    public FactoryBean serviceLocatorFactoryBean() {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(CalculateFactory.class);
        return factoryBean;
    }*/

    /*@Bean(name = "MOEX")
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public MoexCalculateServiceImpl moexCalculator() {
        return new MoexCalculateServiceImpl();
    }

    @Bean(name = "SPB")
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ForeignCalcServiceImpl foreignCalculator() {
        return new ForeignCalcServiceImpl();
    }*/


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
                        false);
            }
        };
    }
}