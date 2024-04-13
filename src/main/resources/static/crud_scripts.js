document.addEventListener('DOMContentLoaded', function () {
    loadCities();
});

function loadCities() {
    fetch('/cities') // Получаем список всех городов
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch cities');
            }
            return response.json();
        })
        .then(cities => {
            const cityListContainer = document.getElementById('cityList');
            cities.forEach(city => {
                const cityDiv = document.createElement('div');
                cityDiv.innerHTML = `
                    <h3>${city.name}</h3>
                    <p>Longitude: ${city.lon}, Latitude: ${city.lat}</p>
                    <button class="button edit-button" onclick="editCity(${city.id})">Edit</button>
                    <button class="button delete-button" onclick="deleteCity(${city.id})">Delete</button>
                `;
                cityListContainer.appendChild(cityDiv);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to fetch cities. Please try again later.');
        });
}

function editCity(cityId) {
    const newName = prompt('Enter new name for the city:');
    if (newName) {
        let newLon;
        let newLat;
        do {
            newLon = prompt('Enter new longitude for the city:');
        } while (!isValidLongitude(newLon));

        do {
            newLat = prompt('Enter new latitude for the city:');
        } while (!isValidLatitude(newLat));

        const data = {
            name: newName,
            lon: parseFloat(newLon),
            lat: parseFloat(newLat)
        };

        fetch(`/cities/${cityId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to edit city');
                }
                alert('City edited successfully.');
                location.reload(); // После изменения обновляем страницу для отображения обновленных данных
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Failed to edit city. Please try again later.');
            });
    }
}

function isValidLongitude(longitude) {
    return /^-?(\d+(\.\d+)?|180)$/.test(longitude) && parseFloat(longitude) >= -180 && parseFloat(longitude) <= 180;
}

function isValidLatitude(latitude) {
    return /^-?(\d+(\.\d+)?|90)$/.test(latitude) && parseFloat(latitude) >= -90 && parseFloat(latitude) <= 90;
}

function deleteCity(cityId) {
    if (confirm('Are you sure you want to delete this city?')) {
        fetch(`/cities/${cityId}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to delete city');
                }
                alert('City deleted successfully.');
                location.reload(); // После удаления обновляем страницу для отображения обновленных данных
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Failed to delete city. Please try again later.');
            });
    }
}
