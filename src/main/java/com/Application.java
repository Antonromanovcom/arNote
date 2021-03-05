package com;

import com.antonromanov.arnote.service.investment.calc.shares.foreign.ForeignCalcServiceImpl;
import com.antonromanov.arnote.service.investment.calc.shares.moex.MoexCalculateServiceImpl;
import com.antonromanov.arnote.service.investment.calc.shares.common.CalculateFactory;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableCaching
public class Application {
    public static void main(String[] args) {
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

    @Bean
    public FactoryBean serviceLocatorFactoryBean() {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(CalculateFactory.class);
        return factoryBean;
    }

   // MOEX("moexCalculator"), SPB("foreignCalculator");

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
                        false);
            }
        };
    }

}
