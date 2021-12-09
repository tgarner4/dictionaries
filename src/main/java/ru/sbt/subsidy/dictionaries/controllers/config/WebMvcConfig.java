package ru.sbt.subsidy.dictionaries.controllers.config;

import java.util.List;
import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import ru.sbt.subsidy.dictionaries.infra.filters.TraceIdInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Bean
  public LocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
    Locale defaultLocale = new Locale("ru", "RU");
    localeResolver.setDefaultLocale(defaultLocale);
    List<Locale> locales = List.of(new Locale("tt", "RU"), defaultLocale);
    localeResolver.setSupportedLocales(locales);
    return localeResolver;
  }


  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new TraceIdInterceptor());
  }
}
