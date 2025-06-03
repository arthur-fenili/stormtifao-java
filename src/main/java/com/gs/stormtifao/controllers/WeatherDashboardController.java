package com.gs.stormtifao.controllers;

import com.gs.stormtifao.services.CohereService;
import com.gs.stormtifao.services.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/dashboard/weather")
public class WeatherDashboardController {

    private final WeatherService weatherService;
    private final CohereService cohereService;

    // Mapeamento de nomes de exibição -> nomes da OpenWeather
    private static final Map<String, String> CIDADE_TO_API = Map.of(
            "São Paulo", "Sao Paulo,BR",
            "Rio de Janeiro", "Rio de Janeiro,BR",
            "Curitiba", "Curitiba,BR"
    );

    public WeatherDashboardController(WeatherService weatherService, CohereService cohereService) {
        this.weatherService = weatherService;
        this.cohereService = cohereService;
    }

    @GetMapping
    public String showForm(@RequestParam(required = false) String cidade, Model model) {
        model.addAttribute("cidades", CIDADE_TO_API.keySet());
        model.addAttribute("selectedCidade", cidade);

        if (cidade != null && !cidade.isEmpty()) {
            // Faz o lookup do nome para a API
            String nomeApi = CIDADE_TO_API.getOrDefault(cidade, cidade);
            String weatherSummary = weatherService.getWeatherSummary(nomeApi);
            String analysis = cohereService.analyzeWeather(weatherSummary);
            model.addAttribute("weatherSummary", weatherSummary);
            model.addAttribute("analysis", analysis);
        }

        return "weather/dashboard";
    }
}
