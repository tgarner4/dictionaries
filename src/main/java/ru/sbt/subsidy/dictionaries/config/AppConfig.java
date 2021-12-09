package ru.sbt.subsidy.dictionaries.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.report.ListReportProvider;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class AppConfig {

  private final ObjectMapper objectMapper;

  @PostConstruct
  private void init() {
    objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.configure(MapperFeature.USE_STD_BEAN_NAMING, true);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @Bean
  public CommonsRequestLoggingFilter logFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeQueryString(true);
    filter.setIncludePayload(true);
    filter.setMaxPayloadLength(100000);
    filter.setIncludeHeaders(false);
    return filter;
  }

  @Bean
  public FilterRegistrationBean<CommonsRequestLoggingFilter> loggingFilterRegistration() {
    FilterRegistrationBean<CommonsRequestLoggingFilter> registration = new FilterRegistrationBean<>(
        logFilter());
    registration.addUrlPatterns("/v1/*");
    return registration;
  }

  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.addBasenames("messages");
    messageSource.setDefaultEncoding("utf-8");
    return messageSource;
  }

  @Bean(name = "validator")
  public LocalValidatorFactoryBean validator() {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(messageSource());
    return bean;
  }

  @Bean
  public JsonSchemaFactory jsonSchemaFactory() {
    return JsonSchemaFactory.newBuilder()
        .setReportProvider(new ListReportProvider(LogLevel.WARNING, LogLevel.ERROR))
        .freeze();
  }
}
