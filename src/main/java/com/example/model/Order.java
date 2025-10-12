package com.example.model;
import com.example.Calculator;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String id;
    private List<Article> articulos;

    public Order() {
        this.articulos = new ArrayList<>();
    }

    public Order(String id, List<Article> articulos) {
        this.id = id;
        this.articulos = (articulos == null) ? new ArrayList<>() : articulos;
    }

    // getters/setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public List<Article> getArticulos() { return articulos; }
    public void setArticulos(List<Article> articulos) {
        this.articulos = (articulos == null) ? new ArrayList<>() : articulos;
    }

    // suma de brutos
    public double getGrossTotal() {
        Calculator c = new Calculator();
        List<Double> nums = new ArrayList<>();
        for (Article a : articulos) {
            nums.add(a.getGrossAmount());
        }
        return c.calculateTotal(nums);
    }

    // suma con descuentos
    public double getDiscountedTotal() {
        Calculator c = new Calculator();
        List<Double> nums = new ArrayList<>();
        for (Article a : articulos) {
            nums.add(a.getDiscountedAmount());
        }
        return c.calculateTotal(nums);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", articulos=" + articulos +
                '}';
    }
}
