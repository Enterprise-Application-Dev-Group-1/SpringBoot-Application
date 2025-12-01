package com.golfhandicapcalculator.enterprise.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class WeatherService {
    private final WebClient webClient;
    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";

    // Approximate coordinates for some common cities
    private static final String DEFAULT_LAT = "28.5383"; // Orlando
    private static final String DEFAULT_LON = "-81.3792";

    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
    }

    public String getWeather(String latitude, String longitude) {
        try {
            JsonNode response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("latitude", latitude)
                            .queryParam("longitude", longitude)
                            .queryParam("current_weather", "true")
                            .queryParam("temperature_unit", "fahrenheit")
                            .queryParam("windspeed_unit", "mph")
                            .build())
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            if (response != null && response.has("current_weather")) {
                JsonNode current = response.get("current_weather");
                double temp = current.get("temperature").asDouble();
                double windSpeed = current.get("windspeed").asDouble();
                int weatherCode = current.get("weathercode").asInt();
                String description = getWeatherDescription(weatherCode);

                return String.format("%.0f°F, %s, Wind: %.0f mph", temp, description, windSpeed);
            }
        } catch (Exception e) {
            return "Weather unavailable";
        }
        return "Weather unavailable";
    }

    private String getWeatherDescription(int code) {
        if (code == 0) return "Clear sky";
        if (code <= 3) return "Partly cloudy";
        if (code <= 48) return "Foggy";
        if (code <= 67) return "Rainy";
        if (code <= 77) return "Snowy";
        if (code <= 82) return "Rain showers";
        if (code <= 86) return "Snow showers";
        return "Thunderstorm";
    }
}
