package com.example.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Servicio sencillo para obtener el tipo de cambio EUR/USD
 * llamando a un servicio REST externo.
 */
public class ExchangeRateService {

    private static final Logger log = LoggerFactory.getLogger(ExchangeRateService.class);

    // api pública
    private static final String URL =
            "https://api.exchangerate.host/latest?base=EUR&symbols=USD";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public double getCurrentEurUsdRate() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() != 200) {
                throw new IllegalStateException("Respuesta HTTP no válida: " + response.statusCode());
            }

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonObject rates = json.getAsJsonObject("rates");
            double rate = rates.get("USD").getAsDouble();

            log.info("Tipo de cambio EUR/USD obtenido del servicio externo: {}", rate);
            return rate;
        } catch (Exception e) {
            log.error("Error obteniendo el tipo de cambio EUR/USD. Uso valor por defecto.", e);
            // Valor de respaldo por si falla el servicio externo
            return 1.10;
        }
    }
}
