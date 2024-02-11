package com.example.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class View implements vInter {

    private BorderPane bp;

    public View(BorderPane bp) {
        this.bp = bp;
    }

    public void Start(MouseEvent event) {
        loadPage("items");
    }

    public void calc(MouseEvent event) {
        loadPage("Calc");
    }

    public void home(MouseEvent event) {
        loadPage("hello-view");
    }

    public void items(MouseEvent event) {
        loadPage("items");
    }

    public void overview(MouseEvent event) {
        loadPage("Overview");
    }


    public void loadPage(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + page + ".fxml"));
            Parent root = loader.load();
            bp.setCenter(root);

            // Zde můžete získat controller, pokud je potřeba
            Object controller = loader.getController();

        } catch (IOException e) {
            System.out.println("Error loading " + page + ".fxml file: " + e.getMessage());
            e.printStackTrace();
            // Zde můžete přidat zobrazení chybové zprávy uživateli
        }
    }

}