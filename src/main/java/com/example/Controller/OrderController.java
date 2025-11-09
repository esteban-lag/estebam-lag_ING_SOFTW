package com.example.Controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.Order;
import com.example.View.OrderView;

public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderView view;
    private final List<Order> orders;
    
    // cuanto vale el dolar
    private static final double USD_RATE = 1.14464;

    public OrderController(OrderView view, List<Order> orders) {
        this.view = view;
        this.orders = orders;

        // cuando apretan el boton hace esto
        this.view.getSearchButton().addActionListener(e -> searchOrder());
        
        log.info("Controller listo con {} ordenes", orders.size());
    }

    // buscar y mostrar
    private void searchOrder() {
        String id = view.getSearchId();
        log.debug("Buscando: {}", id);

        // buscar en la lista
        Order found = orders.stream()
                .filter(o -> o != null && id != null && id.equals(o.getId()))
                .findFirst()
                .orElse(null);

        if (found != null) {
            log.info("Encontre: {}", found.getId());
        } else {
            log.warn("No existe: {}", id);
        }

        // tirar todo a la vista
        view.displayOrder(found, USD_RATE);
    }
}