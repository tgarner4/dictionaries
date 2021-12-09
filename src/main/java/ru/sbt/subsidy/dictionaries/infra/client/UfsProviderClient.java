package ru.sbt.subsidy.dictionaries.infra.client;

public interface UfsProviderClient {


  public static final String DICTIONARY_RESOURCE = "/dictionary";
  public static final String MONITORING_RESOURCE = "/monitoring";
  public static final String SUP_RESOURCE = "/sup";
  public static final String REPORT_RESOURCE = "/print-form";

  String getConfigOptionString(String parameterName, String defaultValue);

  <R, B> R postResponse(String resource,
      Class<R> returnTypeClass,
      B body);
}
