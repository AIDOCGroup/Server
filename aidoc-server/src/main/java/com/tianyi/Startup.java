package com.tianyi;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Created by lingqingwan on 8/4/15
 */
@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class})
@EnableConfigurationProperties(JpaProperties.class)
public class Startup {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Startup.class, args);
    }

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder sessionFactoryBuilder = new LocalSessionFactoryBuilder(
            dataSource)
            .scanPackages("com.tianyi.bo");
        sessionFactoryBuilder.setPhysicalNamingStrategy(new SpringPhysicalNamingStrategy());
        SessionFactory sessionFactory = sessionFactoryBuilder.buildSessionFactory();
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Autowired
    private JpaProperties jpaProperties;

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factory.setPackagesToScan("com.tianyi");

       /* HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setPrepareConnection(false);
        vendorAdapter.setShowSql(jpaProperties.isShowSql());
        vendorAdapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        factory.setJpaVendorAdapter(vendorAdapter);*/

        Map<String, String> jpaMap = Maps.newHashMap();
        jpaMap.put("hibernate.physical_naming_strategy",
            jpaProperties.getHibernate().getNaming().getPhysicalStrategy());
        jpaMap.put("hibernate.show.sql", String.format("%s", jpaProperties.isShowSql()));
        jpaMap.put("hibernate.hbm2ddl.auto", jpaProperties.getHibernate().getDdlAuto());
        factory.setJpaPropertyMap(jpaMap);

        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager jpaTransactionManager(
        EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Collections.singletonList(new ConcurrentMapCache("default")));
        return cacheManager;
    }
}