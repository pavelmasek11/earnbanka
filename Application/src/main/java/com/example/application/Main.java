package com.example.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Připojení k databázi
            Connection connection = MujModel.connection();

            // Inicializace instance MujModel
            MujModel mujModel = new MujModel(connection, null, null, null);

            // Načtení FXML souboru pro HomeController
            FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/lib/hello-view.fxml"));
            Parent homeRoot = homeLoader.load();

            // Načtení FXML souboru pro OverviewController
            FXMLLoader overviewLoader = new FXMLLoader(getClass().getResource("/lib/Overview.fxml"));
            Parent overviewRoot = overviewLoader.load();

            // Vytvoření instance HomeController a předání mujModel
            HomeController homeController = homeLoader.getController();
            homeController.setDatabaseConnection(connection);
            homeController.setMujModel(mujModel);

            // V Main.java, při nastavování scény nebo při přechodu mezi scénami
            OverviewController overviewController = new OverviewController();
            ItemsController itemsController = new ItemsController();

            // Nastavení OverviewController do ItemsController
            itemsController.setOverviewController(overviewController);

            // Vytvoření instance OverviewController a předání mujModel
           // OverviewController overviewController = overviewLoader.getController();
            overviewController.setMujModel(mujModel);

            // Nastavení scény a zobrazení
            Scene homeScene = new Scene(homeRoot);
            primaryStage.setScene(homeScene);
            primaryStage.setTitle("Hello View");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




    /*public void start(Stage primaryStage) {
        try {
            // Připojení k databázi
            Connection connection = MujModel.connection();

            // Načtení FXML souboru
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lib/hello-view.fxml"));
            Parent root = loader.load();

            // Vytvoření instance HomeController
            HomeController controller = loader.getController();
            controller.setDatabaseConnection(connection);

            // Vytvoření instance OverviewController
            OverviewController overviewController = new OverviewController();

            // Vytvoření instance MujModel a předání HomeController a OverviewController
            MujModel mujModel = new MujModel(connection, controller, overviewController);

            // Předání MujModel do HomeController
            controller.setMujModel(mujModel);

            // Nastavení hlavní scény a zobrazení
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hello View");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*
     */
/*
        try {
            Connection connection = MujModel.connection();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml")); // Změna na abs. cestu k souboru
            Parent root = loader.load();

            primaryStage.setTitle("Netusim");
            primaryStage.setScene(new Scene(root)); // Použití již načteného rootu

            HomeController controller = loader.getController();
            controller.setDatabaseConnection(connection);

            primaryStage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
/*
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/hello-view.fxml")));

        Scene scene = new Scene(root, 800, 600);





        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jdbc_lib", "root", "root1234");
            System.out.println("Připojeno");

            Statement statement = connection.createStatement();
            String query = "SELECT * FROM tabulka";
            ResultSet resultSet = statement.executeQuery(query);

            int celkova_castka = 0;

            int denni_urok = 0;
            while (resultSet.next()) {
                celkova_castka = resultSet.getInt("Celkova_castka");
                denni_urok = resultSet.getInt("denni_urok");

                System.out.println("Celková částka: " + celkova_castka + ", Denní úrok: " + denni_urok);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        stage.setTitle("Moje Aplikace");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
        */