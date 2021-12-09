package ru.sbt.subsidy.dictionaries.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.net.URI;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.sbt.subsidy.dictionaries.api.ErrorResponse.ErrorDetailResponse;
import ru.sbt.subsidy.dictionaries.exceptions.NotFoundException;
import ru.sbt.subsidy.dictionaries.exceptions.SubsidyException;
import ru.sbt.subsidy.dictionaries.exceptions.ValidationException;

@RequiredArgsConstructor
@Component
public class BaseHttpClient {

  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  public <T> T getForObject(String url, Class<T> responseType) {
    try {
      return restTemplate.getForObject(url, responseType);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw identifyException(e);
    }
  }

  public <T> T getForObject(String url, ParameterizedTypeReference<T> var) {
    try {
      return restTemplate.exchange(url, HttpMethod.GET, null, var).getBody();
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw identifyException(e);
    } catch (Exception e) {
      throw new SubsidyException(e);
    }
  }

  public void delete(String url) {
    try {
      restTemplate.delete(url);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw identifyException(e);
    } catch (Exception e) {
      throw new SubsidyException(e);
    }
  }

  public <T> T postForObject(String url, Object resourceBody, ParameterizedTypeReference<T> var) {
    try {
      return restTemplate
          .exchange(url, HttpMethod.POST, new HttpEntity<>(resourceBody), var).getBody();
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw identifyException(e);
    } catch (Exception e) {
      throw new SubsidyException(e);
    }
  }

  public <T> T postForObject(URI url, Object resourceBody, Class<T> responseType) {
    try {
      return restTemplate.postForObject(url, resourceBody, responseType);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw identifyException(e);
    } catch (Exception e) {
      throw new SubsidyException(e);
    }
  }

  public <T> T exchange(URI url, Object resourceBody, Class<T> responseType) {
    try {
      return restTemplate
          .exchange(url, HttpMethod.PUT, new HttpEntity<>(resourceBody), responseType).getBody();
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw identifyException(e);
    } catch (Exception e) {
      throw new SubsidyException(e);
    }
  }

  @SneakyThrows
  private SubsidyException identifyException(HttpStatusCodeException e) {
    JsonNode jsonNode = objectMapper.readValue(e.getResponseBodyAsString(), JsonNode.class);
    String message = jsonNode.get("message").asText();
    switch (e.getStatusCode().series()) {
      case SERVER_ERROR:
        return new SubsidyException(message, e);
      case CLIENT_ERROR:
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
          return new NotFoundException(message, e);
        }
        if (jsonNode.get("errors") != null) {
          ArrayNode node = (ArrayNode) jsonNode.get("errors");
          List<ErrorDetailResponse> details = StreamSupport.stream(
              Spliterators.spliteratorUnknownSize(node.iterator(), Spliterator.ORDERED), false)
              .map(n -> new ErrorDetailResponse(n.get("field").asText(), n.get("message").asText()))
              .collect(Collectors.toList());
          return new ValidationException(message, details, e);
        } else {
          return new ValidationException(message, e);
        }
      default:
        return new SubsidyException(message, e);
    }
  }

}
