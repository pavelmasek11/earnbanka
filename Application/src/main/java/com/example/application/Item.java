package com.example.application;

public class Item {
    private String nazev;
    private double goal;
    private double saved;
    // Přidání remains
    private double remains;

    public Item(String nazev, double goal, double saved) {
        this.nazev = nazev;
        this.goal = goal;
        this.saved = saved;
        this.remains = goal - saved; // Výpočet remains při inicializaci
    }

    // Gettery
    public String getNazev() { return nazev; }
    public double getGoal() { return goal; }
    public double getSaved() { return saved; }
    public double getRemains() { return remains; } // Getter pro remains
}