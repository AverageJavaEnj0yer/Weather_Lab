document.addEventListener('DOMContentLoaded', function () {
    // Получаем текущую дату
    const currentDate = new Date().toISOString().slice(0, 10);

    // Устанавливаем текущую дату в значение элемента ввода
    document.getElementById('dateInput').value = currentDate;

    // Вызываем функцию поиска по текущей дате
    searchByDate();
});

function searchByDate() {
    const dateInput = document.getElementById('dateInput').value;
    fetch(`/cities/weatherData?date=${dateInput}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('No answer');
            }
            return response.json();
        })
        .then(data => {
            const weatherDataContainer = document.getElementById('weatherDataContainer');
            weatherDataContainer.innerHTML = ''; // Очищаем контейнер перед добавлением новых данных
            data.forEach(city => {
                displayWeatherData(city);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to fetch weather data. Please try again later.');
        });
}

function displayWeatherData(city) {
    const weatherDataContainer = document.getElementById('weatherDataContainer');

    const cityElement = document.createElement('div');
    cityElement.classList.add('city-item');

    const cityNameElement = document.createElement('h2');
    cityNameElement.textContent = `City: ${city.name}`;

    cityElement.appendChild(cityNameElement);

    city.weatherDataList.forEach(weather => {
        const weatherElement = document.createElement('div');
        weatherElement.classList.add('weather-item');

        const dateElement = document.createElement('p');
        dateElement.textContent = `Date: ${weather.date}`;

        const temperatureElement = document.createElement('p');
        const celsiusTemperature = (weather.temperature - 273.15).toFixed(2); // Конвертация из кельвинов в градусы Цельсия
        temperatureElement.textContent = `Temperature: ${celsiusTemperature}°C`;

        const humidityElement = document.createElement('p');
        humidityElement.textContent = `Humidity: ${weather.humidity.toFixed(2)}%`; // Добавляем знак процента и округляем до двух десятичных знаков

        const weatherConditionElement = document.createElement('p');
        weatherConditionElement.textContent = `Weather Condition: ${weather.weatherConditions[0].description}`;

        weatherElement.appendChild(dateElement);
        weatherElement.appendChild(temperatureElement);
        weatherElement.appendChild(humidityElement);
        weatherElement.appendChild(weatherConditionElement);

        cityElement.appendChild(weatherElement);
    });

    weatherDataContainer.appendChild(cityElement);
}



