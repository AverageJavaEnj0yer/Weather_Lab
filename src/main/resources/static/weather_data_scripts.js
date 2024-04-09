document.addEventListener('DOMContentLoaded', function () {
    fetch('/cities/weatherData?date=2024-04-09') // Замените 2024-04-09 на нужную вам дату
        .then(response => {
            if (!response.ok) {
                throw new Error('No Answer');
            }
            return response.json();
        })
        .then(data => {
            data.forEach(city => {
                displayWeatherData(city);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to fetch weather data. Please try again later.');
        });
});

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
        temperatureElement.textContent = `Temperature: ${weather.temperature}`;

        const humidityElement = document.createElement('p');
        humidityElement.textContent = `Humidity: ${weather.humidity}`;

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

