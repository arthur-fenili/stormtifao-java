package com.gs.stormtifao.controllers;

import com.gs.stormtifao.services.WeatherService;
import com.gs.stormtifao.services.CohereService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final CohereService cohereService;

    public WeatherController(WeatherService weatherService, CohereService cohereService) {
        this.weatherService = weatherService;
        this.cohereService = cohereService;
    }

    @GetMapping("/analyze")
    public String analyzeWeather(@RequestParam String city) {
        String weatherSummary = weatherService.getWeatherSummary(city);
        String analysis = cohereService.analyzeWeather(weatherSummary);
        return "Dados do clima: " + weatherSummary + "\n\nAnálise IA: " + analysis;
    }
}
