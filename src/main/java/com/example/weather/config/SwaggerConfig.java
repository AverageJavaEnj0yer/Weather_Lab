package com.example.weather.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(
        title = "Weather", version = "1.0", license = @License(name = "MIT License", url = "https://github.com/AverageJavaEnj0yer/Weather_Lab/blob/master/LICENSE.md"), contact = @Contact(name = "Daniil", url = "https://t.me/Danii1N", email = "reservaccaynt@gmail.com")), servers = {
        @Server(url = "http://localhost:8080", description = "Local Server")
})

public class SwaggerConfig {

}