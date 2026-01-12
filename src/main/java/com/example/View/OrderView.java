package com.example.View;

import javax.swing.*;
import com.example.model.Order;
import com.example.model.Article;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OrderView extends JFrame {
    private JList<String> orderList;
    private DefaultListModel<String> listModel;
    private JButton viewDetailsButton = new JButton("View Details");
    private JButton createOrderButton = new JButton("Create Order");
    private JButton deleteOrderButton = new JButton("Delete Order");
    private JTextArea detailArea = new JTextArea(15, 50);
    
    public OrderView() {
        setTitle("Order Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // icono
        java.net.URL iconUrl = getClass().getResource("/app.png");
        if (iconUrl != null) {
            Image iconImage = new ImageIcon(iconUrl).getImage();
            setIconImage(iconImage);
        }

        // panel izquierdo con la lista
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.add(new JLabel("Available Orders"), BorderLayout.NORTH);
        
        listModel = new DefaultListModel<>();
        orderList = new JList<>(listModel);
        orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroll = new JScrollPane(orderList);
        listScroll.setPreferredSize(new Dimension(150, 400));
        leftPanel.add(listScroll, BorderLayout.CENTER);
        
        // botones
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(createOrderButton);
        buttonPanel.add(deleteOrderButton);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        // panel derecho con detalles
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.add(new JLabel("Order Details"), BorderLayout.NORTH);
        detailArea.setEditable(false);
        detailArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        rightPanel.add(new JScrollPane(detailArea), BorderLayout.CENTER);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // cargar los ids en la lista
    public void loadOrderIds(List<Order> orders) {
        listModel.clear();
        for (Order o : orders) {
            if (o != null && o.getId() != null) {
                listModel.addElement(o.getId());
            }
        }
    }

    // cual esta seleccionado
    public String getSelectedOrderId() {
        return orderList.getSelectedValue();
    }

    // dar los botones
    public JButton getViewDetailsButton() { return viewDetailsButton; }
    public JButton getCreateOrderButton() { return createOrderButton; }
    public JButton getDeleteOrderButton() { return deleteOrderButton; }

    // mostrar la orden
    public void displayOrder(Order order, double rate) {
        if (order == null) {
            detailArea.setText("Order not found.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Order ").append(order.getId()).append("\n");
        sb.append("=".repeat(50)).append("\n\n");
        
        for (Article art : order.getArticulos()) {
            double unitPrice = art.getPrecio();
            int qty = art.getCantidad();
            double discount = art.getDescuento();
            double netAmount = art.getDiscountedAmount();
            
            sb.append(String.format("%s x%d @ %.2f$ (discount %.1f%%) = %.2f$\n",
                    art.getNombre(), qty, unitPrice, discount, netAmount));
        }
        
        double grossTotal = order.getGrossTotal();
        double discountedTotal = order.getDiscountedTotal();
        
        sb.append("\n");
        sb.append(String.format("Gross total: %.2f$\n", grossTotal));
        sb.append(String.format("Discounted total: %.2f$\n", discountedTotal));
        
        double grossUSD = grossTotal * rate;
        double discountedUSD = discountedTotal * rate;
        
        sb.append("\n");
        sb.append(String.format("Gross total (USD): %.4f$\n", grossUSD));
        sb.append(String.format("Discounted total (USD): %.6f$", discountedUSD));
        
        detailArea.setText(sb.toString());
        detailArea.setCaretPosition(0);
    }

    // formulario para crear pedido
    public Order showCreateOrderDialog() {
        JDialog dialog = new JDialog(this, "Create New Order", true);
        dialog.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(new JLabel("Order ID:"));
        JTextField idField = new JTextField(15);
        topPanel.add(idField);

        JPanel articlesPanel = new JPanel();
        articlesPanel.setLayout(new BoxLayout(articlesPanel, BoxLayout.Y_AXIS));
        articlesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        List<ArticleInputPanel> articleInputs = new ArrayList<>();
        
        JButton addArticleButton = new JButton("Add Article");
        addArticleButton.addActionListener(e -> {
            ArticleInputPanel input = new ArticleInputPanel();
            articleInputs.add(input);
            articlesPanel.add(input);
            articlesPanel.revalidate();
            articlesPanel.repaint();
            dialog.pack();
        });

        topPanel.add(addArticleButton);
        
        JScrollPane articlesScroll = new JScrollPane(articlesPanel);
        articlesScroll.setPreferredSize(new Dimension(600, 300));
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        dialog.add(topPanel, BorderLayout.NORTH);
        dialog.add(articlesScroll, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        Order[] result = new Order[1];

        saveButton.addActionListener(e -> {
            String id = idField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "ID is required");
                return;
            }

            List<Article> articles = new ArrayList<>();
            for (ArticleInputPanel input : articleInputs) {
                try {
                    Article art = input.getArticle();
                    articles.add(art);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error in article data: " + ex.getMessage());
                    return;
                }
            }

            if (articles.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "At least one article is required");
                return;
            }

            result[0] = new Order(id, articles);
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        return result[0];
    }

    // para confirmar borrado
    public boolean confirmDelete(String orderId) {
        int response = JOptionPane.showConfirmDialog(
            this,
            "Delete order " + orderId + "?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        return response == JOptionPane.YES_OPTION;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // panel para ingresar datos de un articulo
    private class ArticleInputPanel extends JPanel {
        private JTextField nameField = new JTextField(15);
        private JTextField qtyField = new JTextField(5);
        private JTextField priceField = new JTextField(8);
        private JTextField discountField = new JTextField(5);

        public ArticleInputPanel() {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            setBorder(BorderFactory.createEtchedBorder());
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            
            add(new JLabel("Name:"));
            add(nameField);
            add(new JLabel("Qty:"));
            add(qtyField);
            add(new JLabel("Price:"));
            add(priceField);
            add(new JLabel("Discount %:"));
            add(discountField);
        }

        public Article getArticle() throws Exception {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                throw new Exception("Name required");
            }
            
            String qtyText = qtyField.getText().trim();
            if (qtyText.isEmpty()) {
                throw new Exception("Quantity required");
            }
            int qty = Integer.parseInt(qtyText);
            if (qty <= 0) {
                throw new Exception("Quantity must be positive");
            }
            
            String priceText = priceField.getText().trim();
            if (priceText.isEmpty()) {
                throw new Exception("Price required");
            }
            double price = Double.parseDouble(priceText);
            if (price < 0) {
                throw new Exception("Price cannot be negative");
            }
            
            String discountText = discountField.getText().trim();
            double discount = 0.0;
            if (!discountText.isEmpty()) {
                discount = Double.parseDouble(discountText);
                if (discount < 0 || discount > 100) {
                    throw new Exception("Discount must be between 0 and 100");
                }
            }

            return new Article(name, qty, price, discount);
        }
    }
}