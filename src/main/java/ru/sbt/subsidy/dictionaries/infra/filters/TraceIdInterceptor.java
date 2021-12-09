package ru.sbt.subsidy.dictionaries.infra.filters;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class TraceIdInterceptor extends HandlerInterceptorAdapter {

  public static final String UUI_ID_HEADER = "X-Trace-Id";
  public static final String REQUEST_TYPE = "requestType";
  public static final String REQUEST_URL = "requestUrl";
  public static final String REQUEST_OPERATION = "requestOperation";
  public static final String PARAMETERS = "parameters";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    final String uuiId = Optional.ofNullable(request.getHeader(UUI_ID_HEADER))
        .orElse(UUID.randomUUID().toString());
    MDC.put(UUI_ID_HEADER, uuiId);
    response.setHeader(UUI_ID_HEADER, uuiId);
    MDC.put(REQUEST_URL, getRequestUrl(request));
    MDC.put(REQUEST_OPERATION, request.getRequestURI());
    MDC.put(REQUEST_TYPE, request.getMethod());
    MDC.put(PARAMETERS, mapToString(request.getParameterMap()));
    return super.preHandle(request, response, handler);
  }

  private String getRequestUrl(HttpServletRequest request) {
    StringBuffer requestUrl = request.getRequestURL();
    if (!StringUtils.isEmpty(request.getQueryString())) {
      requestUrl.append("?").append(request.getQueryString());
    }
    return requestUrl.toString();
  }

  private String mapToString(Map<String, String[]> map) {
    return map.keySet().stream()
        .map(key -> key + "=" + Arrays.toString(map.get(key)))
        .collect(Collectors.joining(", ", "{", "}"));
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView
  ) throws Exception {
    MDC.remove(UUI_ID_HEADER);
    MDC.remove(REQUEST_URL);
    MDC.remove(REQUEST_OPERATION);
    MDC.remove(REQUEST_TYPE);
    MDC.remove(PARAMETERS);

    super.postHandle(request, response, handler, modelAndView);
  }

}
