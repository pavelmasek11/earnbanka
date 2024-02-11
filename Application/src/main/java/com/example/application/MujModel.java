package com.example.application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MujModel {

    private Connection connection;
    private double celkova_castka;
    private double denni_urok;
    private double nasetrena_castka;
    private double pocet_dni;
    private String nazev = "nazev";

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/jdbc_lib";
    private static final String username = "root";
    private static final String password = "root1234";
    private ItemsController itemsController;

    private HomeController homeController;
    private OverviewController overviewController;
    private List<com.example.application.Item> itemList;  // Používáme explicitní import pro typ Item




    public MujModel(Connection connection, HomeController homeController, OverviewController overviewController, ItemsController itemsController) {
        System.out.println("MujModel initialized...");
        this.connection = connection;
        this.homeController = homeController;
        this.overviewController = overviewController;
        this.itemsController = itemsController;
        celkova_castka = 0;
        nasetrena_castka = 0;
        pocet_dni = 0;
        denni_urok = 0;
        loadDataFromDatabase();
        itemList = new ArrayList<>();  // Inicializace Listu pro položky
    }
    public void setOverviewController(OverviewController overviewController) {
        this.overviewController = overviewController;
    }

    private void updateOverview() {
        if (overviewController != null) {
            overviewController.updateOverview();
        }
    }

    public List<com.example.application.Item> getItemsList() {
        return itemList;
    }


    public String getNazev() {
        return nazev;
    }

    public double getNasetrena_castka() {
        return nasetrena_castka;
    }

    public double getCelkova_castka() {
        return celkova_castka;
    }

    public double getDenni_urok() {
        return denni_urok;
    }

    public double zadejSaved(double castka1) {
        nasetrena_castka += castka1;
        System.out.println("Zadali jste nasetrenou castku: " + nasetrena_castka);
        return nasetrena_castka;
    }

    public void zadejNazev(String nazev) {
        this.nazev = nazev;
        System.out.println("Zadali jste text: " + nazev);
    }



    public void zadejGoal(double celkovaCastka) {
        celkova_castka += celkovaCastka;
        System.out.println("Zadali jste celkovou castku: " + celkova_castka);

        Object[] values = {nasetrena_castka, nazev, celkova_castka};
        String query = "UPDATE jdbc_lib.tabulka SET nasetrena_castka = ? WHERE nazev = ? AND celkova_castka = ?";  // Opraveno dotaz

        saveToDatabase(query, values);
    }


    public void pridejPolozku(String nazev, double goal, double saved) {
        com.example.application.Item novaPolozka = new com.example.application.Item(nazev, goal, saved);
        itemList.add(novaPolozka);

        updateOverview();

    }


    public static Connection connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, username, password);
            System.out.println("Connection succeed...");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection failed...");
        }
        return null;
    }

    private void saveToDatabase(String query, Object... values) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Double) {
                    preparedStatement.setDouble(i + 1, (Double) values[i]);
                } else if (values[i] instanceof String) {
                    preparedStatement.setString(i + 1, (String) values[i]);
                }
                // Přidejte další podmínky podle potřeby pro další datové typy
            }
            preparedStatement.executeUpdate();
            System.out.println("Data byla úspěšně uložena do databáze.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Chyba při ukládání do databáze: " + e.getMessage());
        }
    }


    public void loadDataFromDatabase() {
        String query = "SELECT * FROM jdbc_lib.tabulka;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                nazev = resultSet.getString("nazev");
                celkova_castka = resultSet.getDouble("celkova_castka");
                nasetrena_castka = resultSet.getDouble("nasetrena_castka");

                // Zde můžete provádět další operace s načtenými daty
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

