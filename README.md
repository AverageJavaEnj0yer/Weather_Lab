# Проект Погодного Приложения

## Описание
Это простое веб-приложение на Spring Boot, которое использует API OpenWeatherMap для получения прогноза погоды по названию города или географическим координатам.

## Установка и Запуск
1. Клонируйте репозиторий на ваш локальный компьютер.
2. Убедитесь, что у вас установлены Java 21 и Maven 3.Х.Х.
3. Перейдите в директорию проекта и выполните команду `mvn spring-boot:run` для запуска приложения.
4. Приложение будет доступно по адресу `http://localhost:8080`.

## Использование
Приложение предоставляет два конечных пункта API:

1. `/weather?city={city}`: Возвращает прогноз погоды для указанного города.
2. `/weatherByCoordinates?lat={lat}&lon={lon}`: Возвращает прогноз погоды для указанных географических координат.

## Лицензия
Этот проект лицензирован под лицензией MIT.
