package ru.sbt.subsidy.dictionaries.infra.monitoring.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import ru.sbt.subsidy.dictionaries.infra.monitoring.annotations.PerformanceMetric;
import ru.sbt.subsidy.dictionaries.infra.monitoring.services.MonitoringService;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class PerformanceAspect {

  private final MonitoringService monitoringService;

  @Pointcut("@annotation(ru.sbt.subsidy.dictionaries.infra.monitoring.annotations.PerformanceMetric)")
  protected void callsMethods() {
    //Pointcut
  }

  @Before("callsMethods()")
  public void sendCountMetric(JoinPoint joinPoint) {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    PerformanceMetric performanceMetric = methodSignature.getMethod()
        .getAnnotation(PerformanceMetric.class);
    if (StringUtils.hasText(performanceMetric.countRequest())) {
      monitoringService.reportEvent(performanceMetric.countRequest(), 1d);
    }
  }

  @Around("callsMethods()")
  public Object sendCellTimeMetric(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
    PerformanceMetric performanceMetric = methodSignature.getMethod()
        .getAnnotation(PerformanceMetric.class);
    if (StringUtils.isEmpty(performanceMetric.lengthTime())) {
      return proceedingJoinPoint.proceed();
    }

    String className = methodSignature.getDeclaringType().getSimpleName();
    String methodName = methodSignature.getName();
    StopWatch stopWatch = new StopWatch(className + "->" + methodName);
    stopWatch.start(methodName);
    Object result = proceedingJoinPoint.proceed();
    stopWatch.stop();
    monitoringService
        .reportEvent(performanceMetric.lengthTime(), (double) stopWatch.getTotalTimeMillis());
    return result;
  }

}
