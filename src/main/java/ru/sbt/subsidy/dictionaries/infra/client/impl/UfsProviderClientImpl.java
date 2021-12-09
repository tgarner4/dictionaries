package ru.sbt.subsidy.dictionaries.infra.client.impl;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.sbrf.ufs.provider.api.dto.sup.SupRequest;
import ru.sbt.subsidy.dictionaries.infra.client.UfsProviderClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class UfsProviderClientImpl implements UfsProviderClient {

  final HttpServletRequest request;
  private final RestTemplate restTemplate;

  @Value("${api.provider.service.url}")
  private String apiProviderServiceUrl;

  // Обобщенный метод для GET
  public <R> R getResponse(String resource,
      Class<R> returnTypeClass,
      Object body,
      Map<String, ?> queryParams) {
    UriComponentsBuilder builder = UriComponentsBuilder
        .fromHttpUrl(apiProviderServiceUrl + resource);
    queryParams.forEach(builder::queryParam);
    return processResponse(builder.toUriString(), HttpMethod.GET, returnTypeClass, body);
  }

  // Обобщенный метод для POST
  public <R, B> R postResponse(String resource,
      Class<R> returnTypeClass,
      B body) {
    return processResponse(apiProviderServiceUrl + resource, HttpMethod.POST,
        returnTypeClass, body);
  }

  public String getConfigOptionString(String parameterName, String defaultValue) {
    SupRequest supRequest = new SupRequest(parameterName, null);
    try {
      return postResponse(SUP_RESOURCE, String.class, supRequest);
    } catch (Exception e) {
      log.error("Failed to get parameter from SUP: " + parameterName);
      return defaultValue;
    }
  }

  private <R, B> R processResponse(String url, HttpMethod method,
      Class<R> returnTypeClass, B body) {
    HttpHeaders requestHeaders = new HttpHeaders();
    HttpEntity<?> requestEntity = new HttpEntity<>(body, requestHeaders);
    return restTemplate.exchange(url, method, requestEntity, returnTypeClass).getBody();
  }

}
