/**
 * Конфигуратор приложения
 * @author
 * fix Dmitriy Ostrovskiy 18.03.2020
 * created on
 */

package ru.dinikos.backend.task.config;

//import org.h2.tools.Server;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.sql.SQLException;
import java.util.Locale;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("ru.dinikos")
public class AppConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/image_repository/**").addResourceLocations("file:images/");
  }

  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
    sessionLocaleResolver.setDefaultLocale(new Locale("ru"));
    return sessionLocaleResolver;
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("lang");
    return localeChangeInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());
  }

//  @Profile("dev")
//  @Bean(initMethod = "start", destroyMethod = "stop")
//  public Server h2Server() throws SQLException {
//    return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
//  }
}
