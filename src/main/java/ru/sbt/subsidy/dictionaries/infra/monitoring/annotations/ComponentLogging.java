package ru.sbt.subsidy.dictionaries.infra.monitoring.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark classes in which before all method call the function information should be set into
 * the MDC context. All logs of subsequent method calls on any bean will contain the MDC
 * information.
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentLogging {

  String functionName() default "";

}
