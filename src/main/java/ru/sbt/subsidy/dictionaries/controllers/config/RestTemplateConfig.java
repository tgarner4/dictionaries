package ru.sbt.subsidy.dictionaries.controllers.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.slf4j.MDC;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder, ObjectMapper objectMapper) {

    RestTemplate restTemplate = builder
        .additionalInterceptors(getTraceIdInterceptor(), getLocaleInterceptor())
        .build();

    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(
        objectMapper);
    mappingJackson2HttpMessageConverter
        .setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
    restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
    return restTemplate;
  }

  private ClientHttpRequestInterceptor getTraceIdInterceptor() {
    return (httpRequest, bytes, clientHttpRequestExecution) -> {
      Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
      if (copyOfContextMap != null) {
        copyOfContextMap.forEach((k, v) -> httpRequest
            .getHeaders()
            .add(k, v));
      }
      return clientHttpRequestExecution.execute(httpRequest, bytes);
    };
  }

  private ClientHttpRequestInterceptor getLocaleInterceptor() {
    return (httpRequest, bytes, clientHttpRequestExecution) -> {
      Locale loc = LocaleContextHolder.getLocale();
      httpRequest.getHeaders().setAcceptLanguageAsLocales(List.of(loc));
      return clientHttpRequestExecution.execute(httpRequest, bytes);
    };
  }

}
