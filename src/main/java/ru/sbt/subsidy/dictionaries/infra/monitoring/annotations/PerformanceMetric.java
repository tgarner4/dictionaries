package ru.sbt.subsidy.dictionaries.infra.monitoring.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PerformanceMetric {

  String countRequest() default "";

  String lengthTime() default "";

}
