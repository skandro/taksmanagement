package com.imconsulting.employee;

import com.imconsulting.UI.Controller;
import com.imconsulting.employee.privilege.Privilege;
import jakarta.persistence.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class EmployeeEditPanel extends GridPane {
    private final TextField nameTextField = new TextField();
    private final Label nameLabel = new Label("Ime: ");
    private final TextField surnameTextField = new TextField();
    private final Label surnameLabel = new Label("Prezime: ");
    private final TextField usernameTextField = new TextField();
    private final Label usernameLabel = new Label("Korisničko ime: ");
    private final PasswordField passwordField = new PasswordField();
    private final Label passwordLabel = new Label("Lozinka: ");
    private final RadioButton adminRadioButton = new RadioButton("Admin");
    private final RadioButton userRadioButton = new RadioButton("User");
    private final Button saveEmployeeButton = new Button("SAVE");
    private Employee employee;

    public EmployeeEditPanel() {
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(20));

        employee = Controller.getEditEmployee();


        //forma za editovanje
        nameTextField.setMaxWidth(200);
        nameTextField.setText(employee.getName());
        surnameTextField.setMaxWidth(200);
        surnameTextField.setText(employee.getSurname());
        usernameTextField.setMaxWidth(200);
        usernameTextField.setText(employee.getUsername());
        passwordField.setMaxWidth(200);
        passwordField.setText(employee.getPassword());
        add(nameLabel, 0, 0);
        add(nameTextField, 1, 0);
        add(surnameLabel, 0, 1);
        add(surnameTextField, 1, 1);
        add(usernameLabel, 0, 2);
        add(usernameTextField, 1, 2);
        add(passwordLabel, 0, 3);
        add(passwordField, 1, 3);

        //RADIO BUTTON
        if (employee.getPrivilege().getName().equals("admin")) {
            adminRadioButton.setSelected(true);
            userRadioButton.setSelected(false);
        } else {
            adminRadioButton.setSelected(false);
            userRadioButton.setSelected(true);
        }
        ToggleGroup toggleGroup = new ToggleGroup();
        adminRadioButton.setToggleGroup(toggleGroup);
        userRadioButton.setToggleGroup(toggleGroup);
        HBox radioButtonPanel = new HBox(10);
        radioButtonPanel.getChildren().addAll(adminRadioButton, userRadioButton);
        add(radioButtonPanel, 0, 4);


        add(saveEmployeeButton, 0, 5);
        saveEmployeeButton.setOnAction(this::onClickSaveEmployeeButton);

    }

    private void onClickSaveEmployeeButton(ActionEvent actionEvent) {
        if (nameTextField.getText().isEmpty() || surnameTextField.getText().isEmpty() || usernameTextField.getText().isEmpty() || passwordField.getText().isBlank()) {
            Dialog dialog = new Dialog<>();
            dialog.setTitle("Greška");
            dialog.setContentText("Niste popunili sva polja!");
            dialog.show();
            dialog.setHeight(150);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        } else {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Controller.PU_NAME);
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            try {

                Query query = entityManager.createNamedQuery("Employee.findByUsername");
                query.setParameter("username", usernameTextField.getText());
                if (query.getSingleResult() != null && !employee.getUsername().equals(usernameTextField.getText())) {
                    Dialog dialog = new Dialog<>();
                    dialog.setTitle("Greška");
                    dialog.setContentText("Korisničko ime je zauzeto!");
                    dialog.show();
                    dialog.setHeight(150);
                    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                    return;
                }
            } catch (NoResultException e) {
            }
            if (passwordField.getText().length() < 6) {
                Dialog dialog = new Dialog<>();
                dialog.setTitle("Greška");
                dialog.setContentText("Lozinka je prekratka (minimalno 6 karaktera)!");
                dialog.show();
                dialog.setHeight(150);
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                return;
            }
            entityManager.getTransaction().begin();
            Employee editEmployee = entityManager.find(Employee.class, employee.getId());
            editEmployee.setName(nameTextField.getText());
            editEmployee.setSurname(surnameTextField.getText());
            editEmployee.setUsername(usernameTextField.getText());
            editEmployee.setPassword(passwordField.getText());
            if (adminRadioButton.isSelected()) {
                Privilege privilege = entityManager.find(Privilege.class, 1);
                editEmployee.setPrivilege(privilege);
            } else {
                Privilege privilege = entityManager.find(Privilege.class, 2);
                editEmployee.setPrivilege(privilege);
            }
            entityManager.merge(editEmployee);
            entityManager.getTransaction().commit();

            Scene scene = new Scene(new EmployeePanel());
            Controller.instance().getMainStage().setScene(scene);
            Controller.instance().getEditStage().close();
        }
    }
}
