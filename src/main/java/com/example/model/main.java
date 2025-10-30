package com.example.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Iniciando aplicación...");
        
        List<Order> orders = loadOrders("order.json");

        // Log debug para cada orden cargada
        for (Order order : orders) {
            log.debug("Loaded order: {}", order.getId());
            log.debug("  Gross Total: {}", order.getGrossTotal());
            log.debug("  Discounted Total: {}", order.getDiscountedTotal());
        }

        log.info("Total orders loaded: {}", orders.size());
        
        // Mostrar resumen
        for (Order o : orders) {
            log.info("Order {} - Total: ${}", o.getId(), 
                String.format("%.2f", o.getDiscountedTotal()));
        }
    }

    private static List<Order> loadOrders(String resource) {
        log.debug("Loading orders from resource: {}", resource);
        
        InputStream is = Main.class.getResourceAsStream("/" + resource);
        if (is == null) {
            log.error("Missing resource: {} (colócalo en src/main/resources/)", resource);
            throw new IllegalStateException("Missing resource: " + resource +
                    " (colócalo en src/main/resources/)");
        }
        
        try (InputStreamReader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            Type listType = new TypeToken<List<Order>>() {}.getType();
            List<Order> orders = new Gson().fromJson(r, listType);
            log.debug("Successfully loaded {} orders", orders.size());
            return orders;
        } catch (Exception e) {
            log.error("Error reading resource: {}", resource, e);
            throw new RuntimeException("Error reading " + resource, e);
        }
    }
}