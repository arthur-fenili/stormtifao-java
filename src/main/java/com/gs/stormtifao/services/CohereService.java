package com.gs.stormtifao.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CohereService {

    private final String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();

    public CohereService(@Value("${cohere.api.key}") String apiKey) {
        this.apiKey = apiKey;
    }

    public String analyzeWeather(String weatherSummary) {
        String url = "https://api.cohere.ai/v2/chat";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "command-r-plus");
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", "Com base nestes dados meteorológicos, retorne apenas a probabilidade (número percentual) de tempestade ou furacão nas próximas 6 horas: " + weatherSummary + " Justifique em uma linha.");
        messages.add(userMessage);
        requestBody.put("messages", messages);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (weatherSummary == null || weatherSummary.trim().isEmpty()) {
                return "Dados climáticos não disponíveis para análise.";
            }
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> message = (Map<String, Object>) response.getBody().get("message");
                if (message != null) {
                    List<Map<String, String>> contentList = (List<Map<String, String>>) message.get("content");
                    if (contentList != null && !contentList.isEmpty()) {
                        return contentList.get(0).get("text");
                    }
                }
            }
        } catch (Exception e) {
            return "Erro ao consultar Cohere: " + e.getMessage();
        }

        return "Não foi possível obter a análise.";
    }
}
