document.addEventListener('DOMContentLoaded', function () {
    const citySearchButton = document.getElementById('citySearchButton');
    const coordinatesSearchButton = document.getElementById('coordinatesSearchButton');

    // Переключение между режимами поиска
    citySearchButton.addEventListener('click', function () {
        citySearchButton.classList.add('active');
        coordinatesSearchButton.classList.remove('active');
        document.getElementById('citySearchContainer').style.display = 'block';
        document.getElementById('coordinatesSearchContainer').style.display = 'none';
    });

    coordinatesSearchButton.addEventListener('click', function () {
        coordinatesSearchButton.classList.add('active');
        citySearchButton.classList.remove('active');
        document.getElementById('coordinatesSearchContainer').style.display = 'block';
        document.getElementById('citySearchContainer').style.display = 'none';
    });

    const cityInput = document.getElementById('cityInput');
    const submitButton = document.getElementById('submitButton');

    submitButton.addEventListener('click', function () {
        const cityName = cityInput.value.trim();
        if (cityName !== '') {
            fetchWeather(cityName);
        } else {
            alert('Please enter a city name');
        }
    });

    const latitudeInput = document.getElementById('latitudeInput');
    const longitudeInput = document.getElementById('longitudeInput');
    const coordinatesSubmitButton = document.getElementById('coordinatesSubmitButton');

    coordinatesSubmitButton.addEventListener('click', function () {
        const latitude = latitudeInput.value.trim();
        const longitude = longitudeInput.value.trim();
        if (latitude !== '' && longitude !== '') {
            fetchWeatherByCoordinates(latitude, longitude);
        } else {
            alert('Please enter latitude and longitude');
        }
    });

    function fetchWeather(cityName) {
        fetch(`/weather?city=${cityName}&lang=en`)
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

    function fetchWeatherByCoordinates(latitude, longitude) {
        fetch(`/weatherByCoordinates?lat=${latitude}&lon=${longitude}&lang=en`)
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
        weatherConditionElement.textContent = weatherData.weather[0].description; // Change to description
        humidityElement.textContent = `Humidity: ${weatherData.main.humidity}%`;

        // Display weather data after fetching it
        document.getElementById('weatherData').style.display = 'block';
    }
});
