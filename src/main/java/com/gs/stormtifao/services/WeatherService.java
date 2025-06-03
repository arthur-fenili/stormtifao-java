package com.gs.stormtifao.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherService {

    @Value("${openweather.api.key}")
    private String openWeatherApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getWeatherSummary(String city) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("q", city)
                .queryParam("appid", openWeatherApiKey)
                .queryParam("units", "metric")
                .build()
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode root = objectMapper.readTree(response);
            double temp = root.get("main").get("temp").asDouble();
            double humidity = root.get("main").get("humidity").asDouble();
            double wind = root.get("wind").get("speed").asDouble() * 3.6; // m/s para km/h
            String desc = root.get("weather").get(0).get("description").asText();

            return String.format("Cidade: %s. Temperatura: %.1f°C, Umidade: %.0f%%, Vento: %.1f km/h, Condição: %s.",
                    city, temp, humidity, wind, desc);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar dados do OpenWeather", e);
        }
    }
}
