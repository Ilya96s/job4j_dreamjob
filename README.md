# Проект "Работа мечты"

Приложение "Работа мечты". В системе используются две модели: вакансии и кандидаты. Кандидаты будут публиковать резюме. Кадровики будут публиковать вакансии о работе.

Кандидаты могут откликнуться на вакансию. Кадровик может пригласить на вакансию кандидата.

# Стек технологий
* Java 17
* Spring Boot 2.5.2
* PostgreSQL 14
* Slf4j 1.7.36
* HTML 5
* Thymeleaf 2.5.2
* Bootstrap 4.4.1
* H2database 1.4.2
* Junit 5
* Mockito 4.8.0
* Liquibase 4.15.0
* Apache Maven 3.8.5
* Checkstyle-plugin 3.1.2

# Требования к окружению
* Java 17
* Apache Maven 3.8.5
* PostgresSQL 14

# Запуск проекта
1. В PostgreSQL создать базу данных accidents
```shell
jdbc:postgresql://127.0.0.1:5432/dreamjob
```
2. Запустить проект
```shell
mvn spring-boot:run -Pproduction
```
3. Перейти по адресу
```shell
http://localhost:8080/

# Контактные данные
Telegram: https://t.me/ilya96s