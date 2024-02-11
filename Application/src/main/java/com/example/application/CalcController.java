package com.example.application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CalcController {

    @FXML
    private TextField goalTextField;

    @FXML
    private TextField savedTextField;

    @FXML
    private TextField daysTextField;

    @FXML
    private Button calculateButton;

    @FXML
    private TextField resultTextField;

    @FXML
    public void calculateClicked(MouseEvent event) {
        try {
            System.out.println("Metoda calculator byla zavolána.");  // Konzolový výpis

            double goal = Double.parseDouble(goalTextField.getText());
            double saved = Double.parseDouble(savedTextField.getText());
            int days = Integer.parseInt(daysTextField.getText());

            double remainingAmount = goal - saved;
            double dailySavings = remainingAmount / days;
            String formattedResult = String.format("%.2f", dailySavings);

            resultTextField.setText(formattedResult);
        } catch (NumberFormatException e) {
            resultTextField.setText("Zadejte platné číselné hodnoty.");
        }
    }
}
