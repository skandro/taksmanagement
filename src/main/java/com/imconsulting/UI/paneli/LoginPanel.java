package com.imconsulting.UI.paneli;

import com.imconsulting.UI.Controller;
import com.imconsulting.employee.Employee;
import jakarta.persistence.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;


public class LoginPanel extends GridPane {

    private final Label usernameLabel = new Label("Korisničko ime: ");
    private final Label passwordLabel = new Label("Lozinka: ");
    private final TextField usernameTextField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Button loginButton = new Button("Prijava");
    private final Button cancelButton = new Button("Odustani");
    private final Label messageLabel = new Label();
    private final Image image = new Image("logo.png");


    public LoginPanel() {
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));
        setAlignment(Pos.CENTER);

        //username
        add(usernameLabel, 0, 0);
        add(usernameTextField, 1, 0);
        //password
        add(passwordLabel, 0, 1);
        add(passwordField, 1, 1);

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(100);
        add(imageView, 0,3);

        //FlowPane
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER_RIGHT);
        loginButton.setOnAction(this::onLoginButtonClick);
        cancelButton.setOnAction(this::onCancelButtonClick);
        flowPane.setHgap(5);
        flowPane.getChildren().addAll(loginButton, cancelButton);
        add(flowPane, 1, 2);
        //message
        add(messageLabel, 1, 3);
    }

    private void onCancelButtonClick(ActionEvent event) {
        usernameTextField.clear();
        passwordField.clear();
        messageLabel.setText("");
    }


    private void onLoginButtonClick(ActionEvent event) {

        String username = usernameTextField.getText();
        String password = passwordField.getText();
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            messageLabel.setText("Nije dozvoljeno prazan unos korisničkog imena ili lozinke");
            return;
        }
        login(username, password);
    }

    private void login(String username, String password) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Controller.PU_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNamedQuery("Employee.findByUsername");
        query.setParameter("username", username);
        try {
            Employee employee = (Employee) query.getSingleResult();
            if (employee != null && password.equals(employee.getPassword())) {
                Controller.setCurrentEmployee(employee);
                Scene scene = new Scene(new StartPanel());
                Controller.instance().getMainStage().setScene(scene);
                Controller.instance().getMainStage().setTitle("Pocetna");
            } else {
                messageLabel.setText("Neispravna lozinka.");
            }
        } catch (NoResultException e) {
            messageLabel.setText("Nesipravno korisničko ime.");
            System.err.println(e.getMessage());
        }
    }
}
