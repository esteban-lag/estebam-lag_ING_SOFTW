package com.example.model;

import com.example.Controller.OrderController;
import com.example.View.OrderView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingUtilities;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Arrancando...");
        
        // cargar el json
        List<Order> orders = loadOrders("order.json");

        // tirar info por consola
        for (Order order : orders) {
            log.debug("Cargue: {}", order.getId());
            log.debug("  Bruto: {}", order.getGrossTotal());
            log.debug("  Con descuento: {}", order.getDiscountedTotal());
        }

        log.info("Total: {}", orders.size());
        
        // mostrar resumen
        for (Order o : orders) {
            log.info("Orden {} - Total: ${}", o.getId(), 
                String.format("%.2f", o.getDiscountedTotal()));
        }

        // abrir la ventana
        SwingUtilities.invokeLater(() -> {
            log.info("Armando MVC...");
            OrderView view = new OrderView();
            new OrderController(view, orders);
            log.info("Listo pa");
        });
    }

    // leer el json
    private static List<Order> loadOrders(String resource) {
        log.debug("Leyendo: {}", resource);
        
        InputStream is = Main.class.getResourceAsStream("/" + resource);
        if (is == null) {
            log.error("No esta: {}", resource);
            throw new IllegalStateException("Missing resource: " + resource);
        }
        
        try (InputStreamReader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            Type listType = new TypeToken<List<Order>>() {}.getType();
            List<Order> orders = new Gson().fromJson(r, listType);
            log.debug("Cargue {} ordenes", orders.size());
            return orders;
        } catch (Exception e) {
            log.error("Exploto todo: {}", resource, e);
            throw new RuntimeException("Error reading " + resource, e);
        }
    }
}