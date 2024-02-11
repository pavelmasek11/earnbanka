package com.example.application;

import java.sql.Connection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;

import java.io.IOException;

public class HomeController implements MujModelAware {

    @FXML
    private TextArea celkova_castka;
    @FXML
    private TextArea denni_urok;
    @FXML
    private BarChart<String, Number> chart;
    @FXML
    private Main main;
    private MujModel model;
    private Connection databaseConnection;
    @FXML
    private BorderPane bp; // Deklarace proměnné bp v HomeController

    private OverviewController overviewController;
    private Stage primaryStage;
    private Scene overviewScene;




    @Override
    public void setMujModel(MujModel model) {
        this.model = model;
    }

    public HomeController() {
        // Vytvořte instanci OverviewController
        overviewController = new OverviewController();
        overviewController.setOverviewController(this);

    }



    public void setDatabaseConnection(Connection connection) {
        this.databaseConnection = connection;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public void switchToOverviewScene() {
        if (primaryStage != null && overviewScene != null) {
            primaryStage.setScene(overviewScene);
        } else {
            System.out.println("primaryStage or overviewScene is not set.");
        }
    }


    public void setOverviewController(OverviewController overviewController) {
        this.overviewController = overviewController;
        if (overviewController != null) {
            System.out.println("OverviewController is not null.");
            overviewController.setHomeController(this);
        } else {
            System.out.println("OverviewController is null. Cannot set HomeController.");
            // Můžete buď zde vyvolat výjimku nebo provést další řešení podle potřeby.
        }
    }



    @FXML
    public void Start(MouseEvent event) {
        loadPage("items");
    }

    @FXML
    public void Calc(MouseEvent event) {
        loadPage("Calc");
    }

    @FXML
    public void Home(MouseEvent event) {
        loadPage("hello-view");
    }

    @FXML
    public void Items(MouseEvent event) {
        loadPage("items");
    }

    @FXML
    public void Overview(MouseEvent event) {
        loadPage("Overview");
    }

    @FXML
    private void openItems(MouseEvent event) {
        openItemsWindow();
    }


    public void loadPage(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lib/" + page + ".fxml"));
            Parent root = loader.load();

            // Nastavení MujModel do controlleru načítané stránky
            Object controller = loader.getController();
            if (controller instanceof MujModelAware) {
                ((MujModelAware) controller).setMujModel(model);
            }

            bp.setCenter(root);
        } catch (IOException e) {
            System.out.println("Error loading " + page + ".fxml file: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void openItemsWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lib/hello-view.fxml"));

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Items");

            stage.setScene(new Scene(loader.load()));

            ItemsController itemsController = loader.getController();
            itemsController.setMujModel(model);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOverview() {
        try {
            if (overviewScene == null) {
                // Načtení FXML pro Overview a nastavení scény
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/lib/Overview.fxml"));
                Parent root = loader.load();

                // Získání controlleru a nastavení modelu
                overviewController = loader.getController();
                overviewController.setMujModel(model);
                overviewController.setHomeController(this); // Tady je kritický krok, kde se propojí HomeController s OverviewController

                // Vytvoření a nastavení scény, pokud ještě nebyla inicializována
                overviewScene = new Scene(root);
            }

            primaryStage.setScene(overviewScene);
            primaryStage.setTitle("Overview");
            primaryStage.show();

            // Aktualizace dat v OverviewController
            overviewController.updateOverview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}