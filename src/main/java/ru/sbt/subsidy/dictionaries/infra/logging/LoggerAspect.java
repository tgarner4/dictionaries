package ru.sbt.subsidy.dictionaries.infra.logging;

import static net.logstash.logback.argument.StructuredArguments.keyValue;
import static ru.sbt.subsidy.dictionaries.infra.logging.LoggerAspect.AspectStep.AFTER_RETURNING;
import static ru.sbt.subsidy.dictionaries.infra.logging.LoggerAspect.AspectStep.AFTER_THROWING;
import static ru.sbt.subsidy.dictionaries.infra.logging.LoggerAspect.AspectStep.BEFORE;

import java.lang.reflect.Method;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import ru.sbt.subsidy.dictionaries.infra.monitoring.annotations.ComponentLogging;
import ru.sbt.subsidy.dictionaries.infra.monitoring.annotations.MethodLogging;


@Slf4j
@Aspect
@Component
public class LoggerAspect {

  private static final String STEP = "step";
  private static final String METHOD_NAME = "methodName";
  private static final String ARGUMENTS = "arguments";
  private static final String SUCCESS = "success";
  private static final String RESULT = "result";
  private static final String DELEMITER = ".";
  private static final String ASPECT_LOGS = "Aspect logs {} {} {} {} {}";

  @Pointcut(value = "@annotation(ru.sbt.subsidy.dictionaries.infra.monitoring.annotations.MethodLogging)")
  public void methodsAnnotatedWithMethodLogging() {
    // defines pointcut for methods annotated with MethodLogging
  }

  @Pointcut("@within(ru.sbt.subsidy.dictionaries.infra.monitoring.annotations.ComponentLogging)")
  private void classesAnnotatedWithClassLogging() {
    // defines pointcut for classes annotated with ClassWithMdcContext
  }

  @Before("methodsAnnotatedWithMethodLogging()")
  public void aroundAnnotatedMethods(JoinPoint joinPoint) {
    setMdcContextForMethod(joinPoint);
  }

  @Around("classesAnnotatedWithClassLogging()")
  public Object aroundAnnotatedClass(ProceedingJoinPoint joinPoint) throws Throwable {
    setMdcContextForClass(joinPoint);
    return joinPoint.proceed();
  }

  @AfterReturning(pointcut = "methodsAnnotatedWithMethodLogging()", returning = "result")
  public void logAfterMethodLogging(JoinPoint joinPoint, Object result) {
    String resultString = Optional.ofNullable(result)
        .map(Object::toString)
        .orElse("Method is void, has no result");
    log.debug("Result was returned: {}", resultString);
  }

  private void setMdcContextForMethod(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    MethodLogging annotation = method.getAnnotation(MethodLogging.class);
    String functionName = annotation.functionName();
    if (functionName.isBlank()) {
      functionName = getClassName(signature.getDeclaringTypeName()) + DELEMITER + method.getName();
    }
    MDC.put(METHOD_NAME, functionName);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private void setMdcContextForClass(ProceedingJoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Class clazz = signature.getDeclaringType();
    ComponentLogging annotation = (ComponentLogging) clazz.getAnnotation(ComponentLogging.class);
    String functionName = annotation.functionName();
    if (functionName.isBlank()) {
      functionName =
          getClassName(signature.getDeclaringTypeName()) + DELEMITER + signature.getMethod()
              .getName();
    }
    MDC.put(METHOD_NAME, functionName);
  }

  private String getClassName(String classFullName) {
    int startIndexOfClassName = classFullName.lastIndexOf('.') + 1;
    return classFullName.substring(startIndexOfClassName);
  }

  @Pointcut("within(ru.sbt.subsidy..*) && !controllersMethods() && within(org.springframework.web.filter..*)")
  protected void withoutControllerMethods() {
    //Pointcut
  }

  @Pointcut("within(org.springframework.web.client.RestTemplate)")
  public void restTemplateMethods() {
    //Pointcut
  }

  @Pointcut("@within(org.springframework.web.bind.annotation.RestController) ")
  protected void controllersMethods() {
    //Pointcut
  }

  @Before("controllersMethods() || restTemplateMethods() ")
  public void beforeController(JoinPoint joinPoint) {
    Object[] arguments = joinPoint.getArgs();
    log.info(
        "Aspect logs {} {} {}",
        keyValue(STEP, BEFORE.toString()),
        keyValue(METHOD_NAME, joinPoint.getSignature().toString()),
        keyValue(ARGUMENTS, arguments)
    );
  }

  @AfterReturning(pointcut = "controllersMethods() || restTemplateMethods() ", returning = "result")
  public void afterReturningController(JoinPoint joinPoint, Object result) {
    Object[] arguments = joinPoint.getArgs();
    log.info(
        ASPECT_LOGS,
        keyValue(STEP, AFTER_RETURNING.toString()),
        keyValue(METHOD_NAME, joinPoint.getSignature().toString()),
        keyValue(ARGUMENTS, arguments),
        keyValue(SUCCESS, true),
        keyValue(RESULT, result)
    );
  }

  @AfterThrowing(pointcut = "controllersMethods() || restTemplateMethods()", throwing = "error")
  public void afterThrowingWarn(JoinPoint joinPoint, Throwable error) {
    Object[] arguments = joinPoint.getArgs();
    log.warn(
        ASPECT_LOGS,
        keyValue(STEP, AFTER_THROWING.toString()),
        keyValue(METHOD_NAME, joinPoint.getSignature().toString()),
        keyValue(ARGUMENTS, arguments),
        keyValue(SUCCESS, false),
        keyValue(RESULT, error.toString())
    );
  }

  @Before("withoutControllerMethods()")
  public void before(JoinPoint joinPoint) {
    logDebug(BEFORE, joinPoint.getSignature().toString());
    Object[] arguments = joinPoint.getArgs();
    log.trace(
        "Aspect logs {} {} {}",
        keyValue(STEP, BEFORE.toString()),
        keyValue(METHOD_NAME, joinPoint.getSignature().toString()),
        keyValue(ARGUMENTS, arguments)
    );
  }

  @AfterReturning(pointcut = "withoutControllerMethods()", returning = "result")
  public void afterReturning(JoinPoint joinPoint, Object result) {
    logDebug(AFTER_RETURNING, joinPoint.getSignature().toString());
    Object[] arguments = joinPoint.getArgs();
    log.trace(
        "Aspect logs {} {} {} {}",
        keyValue(STEP, AFTER_RETURNING.toString()),
        keyValue(METHOD_NAME, joinPoint.getSignature().toString()),
        keyValue(ARGUMENTS, arguments),
        keyValue(RESULT, result)
    );
  }

  @AfterThrowing(pointcut = "withoutControllerMethods()", throwing = "error")
  public void afterThrowing(JoinPoint joinPoint, Throwable error) {
    logDebug(AFTER_THROWING, joinPoint.getSignature().toString());
    Object[] arguments = joinPoint.getArgs();
    log.trace(
        "Aspect logs {} {} {} {}",
        keyValue(STEP, AFTER_THROWING.toString()),
        keyValue(METHOD_NAME, joinPoint.getSignature().toString()),
        keyValue(ARGUMENTS, arguments),
        keyValue(RESULT, error.toString())
    );
  }

  private void logDebug(AspectStep step, String methodName) {
    log.debug(
        "Aspect logs {} {}",
        keyValue(STEP, step),
        keyValue(METHOD_NAME, methodName)
    );
  }

  @RequiredArgsConstructor
  enum AspectStep {
    BEFORE("============@Before=========="),
    AFTER_RETURNING("============@AfterReturning=========="),
    AFTER_THROWING("============@AfterThrowing==========");

    private final String value;

    @Override
    public String toString() {
      return this.value;
    }
  }
}
