document.addEventListener('DOMContentLoaded', function () {
    const cityInput = document.getElementById('cityInput');
    const submitButton = document.getElementById('submitButton');
    const weatherDataDiv = document.getElementById('weatherData');

    submitButton.addEventListener('click', function () {
        const cityName = cityInput.value.trim();
        if (cityName !== '') {
            fetchWeather(cityName);
        } else {
            alert('Пожалуйста, введите название города');
        }
    });

    function fetchWeather(cityName) {
        fetch(`/weather?city=${cityName}&lang=ru`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch weather data');
                }
                return response.json();
            })
            .then(data => {
                displayWeatherData(data);
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Failed to fetch weather data. Please try again later.');
            });
    }


    function displayWeatherData(weatherData) {
        const temperatureCelsius = Math.round(weatherData.main.temp - 273.15);
        const iconUrl = `https://openweathermap.org/img/wn/${weatherData.weather[0].icon}.png`;
        const cityNameElement = document.getElementById('cityName');
        const temperatureElement = document.getElementById('temperature');
        const weatherIconElement = document.getElementById('weatherIcon');
        const weatherConditionElement = document.getElementById('weatherCondition');
        const humidityElement = document.getElementById('humidity');

        cityNameElement.textContent = weatherData.name;
        temperatureElement.textContent = `${temperatureCelsius}°C`;
        weatherIconElement.src = iconUrl;
        weatherConditionElement.textContent = weatherData.weather[0].description; // Изменение на description
        humidityElement.textContent = `Влажность: ${weatherData.main.humidity}%`;
    }






});
