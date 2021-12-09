package ru.sbt.subsidy.dictionaries.infra.monitoring.services;

public interface MonitoringService {

  void reportEvent(String metricName, double value);

  void reportEvent(String metricName, double value, String path);

}
