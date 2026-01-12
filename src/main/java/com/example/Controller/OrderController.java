package com.example.Controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.Order;
import com.example.View.OrderView;
import com.example.model.ExchangeRateService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderView view;
    private final List<Order> orders;
    private final ExchangeRateService exchangeRateService;

    public OrderController(OrderView view, List<Order> orders) {
        this.view = view;
        this.orders = orders;
        this.exchangeRateService = new ExchangeRateService();

        // cargar ids iniciales
        view.loadOrderIds(orders);

        // conectar botones
        view.getViewDetailsButton().addActionListener(e -> viewOrderDetails());
        view.getCreateOrderButton().addActionListener(e -> createOrder());
        view.getDeleteOrderButton().addActionListener(e -> deleteOrder());

        log.info("Controller listo con {} ordenes", orders.size());
    }

    // ver detalles del pedido seleccionado
    private void viewOrderDetails() {
        String id = view.getSelectedOrderId();
        if (id == null || id.isEmpty()) {
            view.showMessage("Select an order first");
            return;
        }

        log.debug("Buscando: {}", id);

        Order found = null;
        for (Order o : orders) {
            if (o != null && o.getId() != null && o.getId().equals(id)) {
                found = o;
                break;
            }
        }

        if (found != null) {
            log.info("Encontre: {}", found.getId());
        } else {
            log.warn("No existe: {}", id);
        }

        double rate = exchangeRateService.getCurrentEurUsdRate();
        view.displayOrder(found, rate);
    }

    // crear nuevo pedido
    private void createOrder() {
        Order newOrder = view.showCreateOrderDialog();
        
        if (newOrder == null) {
            log.debug("Creacion cancelada");
            return;
        }

        // chequear que el id no exista
        boolean exists = false;
        for (Order o : orders) {
            if (o != null && o.getId() != null && o.getId().equals(newOrder.getId())) {
                exists = true;
                break;
            }
        }
        
        if (exists) {
            view.showMessage("Order ID already exists");
            log.warn("ID duplicado: {}", newOrder.getId());
            return;
        }

        orders.add(newOrder);
        saveOrdersToFile();
        view.loadOrderIds(orders);
        
        log.info("Pedido creado: {}", newOrder.getId());
        view.showMessage("Order created successfully");
    }

    // borrar pedido
    private void deleteOrder() {
        String id = view.getSelectedOrderId();
        if (id == null || id.isEmpty()) {
            view.showMessage("Select an order first");
            return;
        }

        if (!view.confirmDelete(id)) {
            log.debug("Borrado cancelado");
            return;
        }

        Order toRemove = null;
        for (Order o : orders) {
            if (o != null && o.getId() != null && o.getId().equals(id)) {
                toRemove = o;
                break;
            }
        }
        
        boolean removed = false;
        if (toRemove != null) {
            removed = orders.remove(toRemove);
        }
        
        if (removed) {
            saveOrdersToFile();
            view.loadOrderIds(orders);
            view.displayOrder(null, 0);
            
            log.info("Pedido borrado: {}", id);
            view.showMessage("Order deleted successfully");
        } else {
            log.warn("No se pudo borrar: {}", id);
            view.showMessage("Error deleting order");
        }
    }

    // guardar en el json
    private void saveOrdersToFile() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(orders);
        
        // escribir en target/classes/order.json
        String targetPath = "target/classes/order.json";
        writeJsonToFile(targetPath, json);
        
        // tambien en src para que persista
        String srcPath = "src/main/resources/order.json";
        writeJsonToFile(srcPath, json);
        
        log.info("Archivo guardado");
    }

    private void writeJsonToFile(String path, String json) {
        File file = new File(path);
        File parentDir = file.getParentFile();
        
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json);
        } catch (IOException e) {
            log.error("Error guardando archivo en {}", path, e);
            view.showMessage("Error saving changes to file: " + path);
        }
    }
}