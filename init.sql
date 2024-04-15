CREATE TABLE City (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      lon DOUBLE PRECISION NOT NULL,
                      lat DOUBLE PRECISION NOT NULL
);

CREATE TABLE WeatherCondition (
                                  id SERIAL PRIMARY KEY,
                                  main VARCHAR(255) NOT NULL,
                                  description VARCHAR(255) NOT NULL,
                                  icon VARCHAR(255) NOT NULL
);

CREATE TABLE WeatherData (
                             id SERIAL PRIMARY KEY,
                             date DATE NOT NULL,
                             temperature DOUBLE PRECISION NOT NULL,
                             humidity DOUBLE PRECISION NOT NULL,
                             city_id INT REFERENCES City(id)
);

CREATE TABLE WeatherData_WeatherCondition (
                                              weatherData_id INT REFERENCES WeatherData(id),
                                              weatherCondition_id INT REFERENCES WeatherCondition(id),
                                              PRIMARY KEY (weatherData_id, weatherCondition_id)
);
