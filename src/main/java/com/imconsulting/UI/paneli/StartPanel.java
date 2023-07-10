package com.imconsulting.UI.paneli;


import com.imconsulting.UI.Controller;
import com.imconsulting.company.CompanyPanel;
import com.imconsulting.customer.CustomerPanel;
import com.imconsulting.employee.EmployeePanel;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.time.LocalDate;

public class StartPanel extends VBox {
    private final Label currentEmployeeLabel = new Label();
    private final Label welcomeLabel = new Label("Welcome to the application - task management");
    private final Button customersButton = new Button("Customers");
    private final Button employeesButton = new Button("Employees");

    private final Button companiesButton = new Button("Companies");
    private final Button logoutButton = new Button("Odjava");
    private final Label dateLabel = new Label();
    private final Image image = new Image("logo.png");

    public StartPanel() {
        setSpacing(10);
        setPadding(new Insets(10));

        dateLabel.setText(returnDate());
        currentEmployeeLabel.setText(Controller.getCurrentEmployee().getName() + ", " + Controller.getCurrentEmployee().getSurname());
        BorderPane dateAndEmployeePanel = new BorderPane(null, null, currentEmployeeLabel, null, dateLabel);

        BorderPane welcomePanel = new BorderPane(welcomeLabel);
        welcomeLabel.setFont(new Font("Arial", 20));
        welcomePanel.setPadding(new Insets(20));

        FlowPane buttonPanel = new FlowPane();
        buttonPanel.setHgap(10);
        buttonPanel.setAlignment(Pos.CENTER);
        buttonPanel.getChildren().addAll(customersButton, employeesButton, companiesButton, logoutButton);

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(150);
        imageView.setFitWidth(250);
        BorderPane imagePanel = new BorderPane(imageView);


        getChildren().addAll(dateAndEmployeePanel, welcomePanel, buttonPanel, imagePanel);


        logoutButton.setOnAction(this::onClickLogoutButton);
        customersButton.setOnAction(this::onClickCustomerButton);
        employeesButton.setOnAction(this::onClickEmployeeButton);
        companiesButton.setOnAction(this::onCLickCompaniesButton);

        //Image middleImage=new Image("middle.gif");
        //ImageView imageView1 = new ImageView(middleImage);
        //customersButton.setGraphic(imageView1);
    }

    private void onCLickCompaniesButton(ActionEvent actionEvent) {
        CompanyPanel companyPanel = new CompanyPanel();
        Scene scene = new Scene(companyPanel);
        Controller.instance().getMainStage().setScene(scene);
        Controller.instance().getMainStage().setTitle("Companies");
    }

    private void onClickCustomerButton(ActionEvent actionEvent) {
        CustomerPanel customerPanel = new CustomerPanel();
        Scene scene = new Scene(customerPanel);
        Controller.instance().getMainStage().setScene(scene);
        Controller.instance().getMainStage().setTitle("Customers");
    }

    private void onClickEmployeeButton(ActionEvent actionEvent) {
        EmployeePanel employeePanel = new EmployeePanel();
        Scene scene = new Scene(employeePanel);
        Controller.instance().getMainStage().setScene(scene);
        Controller.instance().getMainStage().setTitle("Employees");
    }

    private void onClickLogoutButton(ActionEvent actionEvent) {
        LoginPanel loginPanel = new LoginPanel();
        Scene scene = new Scene(loginPanel);
        Controller.instance().getMainStage().setScene(scene);
        Controller.instance().getMainStage().setTitle("IM-Consulting");
    }

    private String returnDate() {
        LocalDate localDate = LocalDate.now();
        return localDate.toString();
    }

}



