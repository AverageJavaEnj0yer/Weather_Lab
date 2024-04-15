CREATE TABLE City (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      lon DOUBLE PRECISION NOT NULL,
                      lat DOUBLE PRECISION NOT NULL
);

INSERT INTO City (name, lon, lat) VALUES ('Москва', 37.6173, 55.7558);
INSERT INTO City (name, lon, lat) VALUES ('Санкт-Петербург', 30.3351, 59.9343);

CREATE TABLE WeatherCondition (
                                  id SERIAL PRIMARY KEY,
                                  main VARCHAR(255) NOT NULL,
                                  description VARCHAR(255) NOT NULL,
                                  icon VARCHAR(255) NOT NULL
);

INSERT INTO WeatherCondition (main, description, icon) VALUES ('Clear', 'ясно', 'clear-sky');
INSERT INTO WeatherCondition (main, description, icon) VALUES ('Clouds', 'облачно', 'cloudy');
INSERT INTO WeatherCondition (main, description, icon) VALUES ('Rain', 'дождь', 'rain');

CREATE TABLE WeatherData (
                             id SERIAL PRIMARY KEY,
                             date DATE NOT NULL,
                             temperature DOUBLE PRECISION NOT NULL,
                             humidity DOUBLE PRECISION NOT NULL,
                             city_id INT,
                             FOREIGN KEY (city_id) REFERENCES City(id)
);

CREATE TABLE WeatherData_WeatherCondition (
                                              weatherData_id INT,
                                              weatherCondition_id INT,
                                              FOREIGN KEY (weatherData_id) REFERENCES WeatherData(id),
                                              FOREIGN KEY (weatherCondition_id) REFERENCES WeatherCondition(id)
);
