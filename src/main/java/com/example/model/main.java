package com.example.model;

import com.example.model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getName());
    
    static{ 
              System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n");

        Logger root = Logger.getLogger("");
        root.setLevel(Level.FINE);

        for (Handler h : root.getHandlers()) {
            if (h instanceof ConsoleHandler) {
                h.setLevel(Level.FINE);
                h.setFormatter(new SimpleFormatter());
     }
    }
}

    public static void main(String[] args) {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINE);
        log.setUseParentHandlers(false);
        log.addHandler(handler);
        log.setLevel(Level.FINE);

        List<Order> orders = loadOrders("order.json");

        log.info("Total orders loaded: " + orders.size());
        for (Order o : orders) {
            log.fine("Loaded order: " + o.getId()); // equivalente a debug
        }
    }

    private static List<Order> loadOrders(String resource) {
        InputStream is = Main.class.getResourceAsStream("/" + resource);
        if (is == null) {
            throw new IllegalStateException("Missing resource: " + resource +
                    " (colócalo en src/main/resources/)");
        }
        try (InputStreamReader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            Type listType = new TypeToken<List<Order>>() {}.getType();
            return new Gson().fromJson(r, listType);
        } catch (Exception e) {
            throw new RuntimeException("Error reading " + resource, e);
        }
    }
}
