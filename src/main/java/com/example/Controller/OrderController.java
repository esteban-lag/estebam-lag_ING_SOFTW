package com.example.Controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.Order;
import com.example.View.OrderView;
import com.example.model.ExchangeRateService;

public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderView view;
    private final List<Order> orders;

    // Servicio para obtener el tipo de cambio EUR/USD
    private final ExchangeRateService exchangeRateService;

    public OrderController(OrderView view, List<Order> orders) {
        this.view = view;
        this.orders = orders;
        this.exchangeRateService = new ExchangeRateService();

        // cuando aprietan el botón hace esto
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

        // obtener tipo de cambio actual EUR/USD llamando al servicio externo
        double currentEurUsdRate = exchangeRateService.getCurrentEurUsdRate();

        // tirar todo a la vista
        view.displayOrder(found, currentEurUsdRate);
    }
}
