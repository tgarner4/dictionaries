package ru.sbt.subsidy.dictionaries.infra.monitoring.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import ru.sbt.subsidy.dictionaries.infra.monitoring.annotations.ConnectionMetric;
import ru.sbt.subsidy.dictionaries.infra.monitoring.services.MonitoringService;


@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ConnectionAspect {

  private final MonitoringService monitoringService;

  @Pointcut("@annotation(ru.sbt.subsidy.dictionaries.infra.monitoring.annotations.ConnectionMetric)")
  protected void callsMethods() {
    //Pointcut
  }

  @AfterReturning("callsMethods()")
  public void sendSuccessMetric(JoinPoint joinPoint) {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    ConnectionMetric metric = methodSignature.getMethod().getAnnotation(ConnectionMetric.class);
    if (StringUtils.hasText(metric.success())) {
      monitoringService.reportEvent(metric.success(), 1d);
    }
  }

  @AfterThrowing(pointcut = "callsMethods()", throwing = "error")
  public void sendErrorMetric(JoinPoint joinPoint, Throwable error) {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    ConnectionMetric metric = methodSignature.getMethod().getAnnotation(ConnectionMetric.class);
    if (StringUtils.hasText(metric.timeOut()) && isTimeOutError(error)) {
      monitoringService.reportEvent(metric.timeOut(), 1d);
    } else if (StringUtils.hasText(metric.error())) {
      monitoringService.reportEvent(metric.error(), 1d);
    }
  }

  private boolean isTimeOutError(Throwable error) {
    return error instanceof ResourceAccessException;
  }

}
