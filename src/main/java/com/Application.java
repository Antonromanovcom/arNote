package com;

//todo: Почему в пакете com, а не в пакете com.antonromanov.arnote ??? Исправить !!!!!!
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
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