CREATE TABLE IF NOT EXISTS City (
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL,
                                    lon DOUBLE PRECISION NOT NULL,
                                    lat DOUBLE PRECISION NOT NULL
);

-- Создание таблицы WeatherCondition
CREATE TABLE IF NOT EXISTS WeatherCondition (
                                                id SERIAL PRIMARY KEY,
                                                main VARCHAR(255) NOT NULL,
                                                description VARCHAR(255) NOT NULL,
                                                icon VARCHAR(255) NOT NULL
);

-- Создание таблицы WeatherData
CREATE TABLE IF NOT EXISTS WeatherData (
                                           id SERIAL PRIMARY KEY,
                                           date DATE NOT NULL,
                                           temperature DOUBLE PRECISION NOT NULL,
                                           humidity DOUBLE PRECISION NOT NULL,
                                           city_id INT,
                                           FOREIGN KEY (city_id) REFERENCES City(id)
);

-- Создание связи между таблицами WeatherData и WeatherCondition
CREATE TABLE IF NOT EXISTS WeatherData_WeatherCondition (
                                                            weatherData_id INT,
                                                            weatherCondition_id INT,
                                                            FOREIGN KEY (weatherData_id) REFERENCES WeatherData(id),
                                                            FOREIGN KEY (weatherCondition_id) REFERENCES WeatherCondition(id)
);