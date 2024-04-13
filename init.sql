-- Создание таблицы City
CREATE TABLE IF NOT EXISTS City (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL,
                                    lon DOUBLE NOT NULL,
                                    lat DOUBLE NOT NULL
);

-- Создание таблицы WeatherCondition
CREATE TABLE IF NOT EXISTS WeatherCondition (
                                                id INT AUTO_INCREMENT PRIMARY KEY,
                                                main VARCHAR(255) NOT NULL,
                                                description VARCHAR(255) NOT NULL,
                                                icon VARCHAR(255) NOT NULL
);

-- Создание таблицы WeatherData
CREATE TABLE IF NOT EXISTS WeatherData (
                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                           date DATE NOT NULL,
                                           temperature DOUBLE NOT NULL,
                                           humidity DOUBLE NOT NULL,
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
