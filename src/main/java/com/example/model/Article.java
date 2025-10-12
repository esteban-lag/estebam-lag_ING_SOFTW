package com.example.model;

import com.example.Calculator;

public class Article {
    private String nombre;
    private int cantidad;
    private double precio;
    private double descuento;

    public Article() {}

    public Article(String nombre, int cantidad, double precio, double descuento) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descuento = descuento;
    }

    // getters/setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }

    // bruto = cantidad x precio
    public double getGrossAmount() {
        Calculator c = new Calculator();
        return c.multiplydouble(precio, (double) cantidad);
    }

    // aplica porcentaje al bruto
    public double getDiscountedAmount() {
        Calculator c = new Calculator();
        return c.discount(getGrossAmount(), descuento);
    }

    @Override
    public String toString() {
        return "Article{" +
                "nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                ", descuento=" + descuento +
                '}';
    }
}
