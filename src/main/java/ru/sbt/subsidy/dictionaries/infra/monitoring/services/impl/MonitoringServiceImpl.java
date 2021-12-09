package ru.sbt.subsidy.dictionaries.infra.monitoring.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sbrf.ufs.provider.api.dto.monitoring.MonitoringMetricRequest;
import ru.sbt.subsidy.dictionaries.infra.client.UfsProviderClient;
import ru.sbt.subsidy.dictionaries.infra.monitoring.services.MonitoringService;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonitoringServiceImpl implements MonitoringService {

  private final UfsProviderClient ufsProviderClientService;

  @Value("pprb.monitoring.path")
  private String path;

  private void reportEvent(MonitoringMetricRequest metricRequest) {
    log.info(metricRequest.toString());
  }

  public void reportEvent(String metricName, double value) {
    reportEvent(new MonitoringMetricRequest(metricName, value, this.path));
  }

  public void reportEvent(String metricName, double value, String path) {
    reportEvent(new MonitoringMetricRequest(metricName, value, path));
  }
}
