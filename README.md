# subsidy-back-template

Шаблон микросервиса.

## Сборка проекта

`mvn clean install`

## Запуск проекта

`mvn spring-boot:run`

Запустится на порту 8080.

## Локальный запуск unit тестов

`mvn verify -DskipITs=true`

## Локальный запуск интеграционных тестов

`mvn verify -DskipUTs=true`

## Локальный запуск образа в Docker
Последовательно запустить команды
`mvn clean package docker:build`

`docker-compose -f docker/docker-compose-test.yml up -d`
