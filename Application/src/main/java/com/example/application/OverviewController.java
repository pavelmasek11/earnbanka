package com.example.application;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;

public class OverviewController implements MujModelAware {

    private List<Item> itemList = new ArrayList<>();

    @FXML
    private TableView<Item> tableView;
    private MujModel model;
    private HomeController homeController;

    @FXML
    private BarChart<String, Number> itemBarChart;

    @FXML
    private TableColumn<Item, Double> remainsColumn;

    @FXML
    private ComboBox<String> itemComboBox; // Přidání ComboBoxu pro výběr položek
    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    public void setOverviewController(HomeController homeController) {
        this.homeController = homeController;
    }

    @FXML
    private void initialize() {
        Connection databaseConnection = MujModel.connection();
        this.model = new MujModel(databaseConnection, homeController, this, null);
        model.loadDataFromDatabase();

        remainsColumn.setCellValueFactory(new PropertyValueFactory<>("remains"));
        itemList = model.getItemsList();
        tableView.getItems().setAll(itemList);
        loadItemsToComboBox();
    }

    private void loadItemsToComboBox() {
        List<String> itemNames = new ArrayList<>();
        for (Item item : itemList) {
            itemNames.add(item.getNazev());
        }
        itemComboBox.setItems(FXCollections.observableArrayList(itemNames));
        itemComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateBarChartBasedOnSelection(newValue);
        });
    }

    public void addItemToComboBox(String itemName) {
        if (!itemComboBox.getItems().contains(itemName)) {
            itemComboBox.getItems().add(itemName);
        }
        itemComboBox.getSelectionModel().select(itemName);
    }

    
    private void updateBarChartBasedOnSelection(String itemName) {
        Item selectedItem = findItemByName(itemName);
        if (selectedItem != null) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("Goal", selectedItem.getGoal()));
            series.getData().add(new XYChart.Data<>("Saved", selectedItem.getSaved()));
            series.getData().add(new XYChart.Data<>("Remains", selectedItem.getGoal() - selectedItem.getSaved()));

            itemBarChart.getData().clear();
            itemBarChart.getData().add(series);
        }
    }

    private Item findItemByName(String name) {
        for (Item item : itemList) {
            if (item.getNazev().equals(name)) {
                return item;
            }
        }
        return null;
    }

    private void distributeColumnWidths() {
        ObservableList<TableColumn<Item, ?>> columns = tableView.getColumns();
        for (TableColumn<Item, ?> column : columns) {
            column.prefWidthProperty().bind(tableView.widthProperty().divide(columns.size()));
        }
    }

    @FXML
    public void updateOverview() {
        itemList = model.getItemsList();
        tableView.getItems().setAll(itemList);
        updateBarChartBasedOnSelection(itemComboBox.getSelectionModel().getSelectedItem()); // Aktualizace grafu také, když se obnovuje přehled
    }

    @Override
    public void setMujModel(MujModel model) {
        this.model = model;
    }
}
