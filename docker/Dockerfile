FROM base.sw.sbc.space/tatar_dev/tatarsubsidy_dev/infra-images/openjdk11tz:0.1.0.1
ENV SPRING_PROFILES_ACTIVE=prod
COPY target/*-spring-boot.jar /subsidy-backend-dictionaries.jar
ENTRYPOINT ["java", "-jar", "/subsidy-backend-dictionaries.jar"]
