package com.example.View;

import javax.swing.*;
import com.example.model.Order;
import com.example.model.Article;
import java.awt.*;

public class OrderView extends JFrame {
    private JTextField searchField = new JTextField(10);
    private JButton searchButton = new JButton("Search");
    private JTextArea resultArea = new JTextArea(10, 40);

    public OrderView() {
        setTitle("Order Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // tirar todo a la ventana
        add(new JLabel("Order ID:"));
        add(searchField);
        add(searchButton);
        
        // que no se pueda escribir aca
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea));

        pack();
        setVisible(true);
    }

    // agarra lo que escribio el user
    public String getSearchId() {
        return searchField.getText().trim();
    }

    // dar el boton
    public JButton getSearchButton() {
        return searchButton;
    }

    // mostrar la orden aca
    public void displayOrder(Order order, double rate) {
        if (order == null) {
            resultArea.setText("Order not found.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Order ").append(order.getId()).append("\n");
        
        // recorrer los articulos
        for (Article art : order.getArticulos()) {
            double unitPrice = art.getPrecio();
            int qty = art.getCantidad();
            double discount = art.getDescuento();
            
            double grossAmount = art.getGrossAmount();
            double discountAmount = grossAmount * discount / 100.0;
            double netAmount = art.getDiscountedAmount();
            
            sb.append(String.format("%s x%d @ %.2f$ (discount %.1f%%) = %.2f$\n",
                    art.getNombre(), qty, unitPrice, discount, netAmount));
        }
        
        // sacar totales
        double grossTotal = order.getGrossTotal();
        double discountedTotal = order.getDiscountedTotal();
        
        sb.append(String.format("Gross total: %.2f$\n", grossTotal));
        sb.append(String.format("Discounted total: %.2f$\n", discountedTotal));
        
        // pasar a dolares
        double grossUSD = grossTotal * rate;
        double discountedUSD = discountedTotal * rate;
        
        sb.append(String.format("Gross total (USD): %.4f$\n", grossUSD));
        sb.append(String.format("Discounted total (USD): %.6f$", discountedUSD));
        
        resultArea.setText(sb.toString());
    }
}