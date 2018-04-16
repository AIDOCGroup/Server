package com.tianyi;

import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

/**
 * Created by lingqingwan on 8/4/15
 */
@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class })
@ImportResource("classpath:spring-*.xml")
public class Startup {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Startup.class, args);
    }
    
     @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder sessionFactoryBuilder = new LocalSessionFactoryBuilder(
            dataSource)
            .scanPackages("com.tianyi.bo");
        sessionFactoryBuilder.setPhysicalNamingStrategy(new SpringPhysicalNamingStrategy());

        SessionFactory sessionFactory = sessionFactoryBuilder.buildSessionFactory();
        return sessionFactory;
    }
}
