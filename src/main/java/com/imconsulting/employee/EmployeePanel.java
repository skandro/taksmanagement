package com.imconsulting.employee;


import com.imconsulting.UI.Controller;
import com.imconsulting.UI.paneli.StartPanel;
import com.imconsulting.employee.privilege.Privilege;
import jakarta.persistence.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeePanel extends VBox {
    private final Label currentEmployeeLabel = new Label();
    private final Button backButton = new Button("Back");
    private final TableView<Employee> employeeTableView = new TableView<>();
    private ObservableList<Employee> employeeObservableList;
    private EmployeeController employeeController = new EmployeeController();
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
    private final Button addEmployeeButton = new Button("Add Employee");
    private final Button editEmployeeButton = new Button("Edit Employee");
    private final Button deleteEmployeeButton = new Button("Delete Employee");
    private final CheckBox deleteCheckBox = new CheckBox("Delete");

    public EmployeePanel() {
        setSpacing(10);
        setPadding(new Insets(10));

        currentEmployeeLabel.setText(Controller.getCurrentEmployee().getName() + ", " + Controller.getCurrentEmployee().getSurname());
        BorderPane backButtonAndEmployeePanel = new BorderPane(null, null, currentEmployeeLabel, null, backButton);

        setupTableView();
        HBox buttonPanel = setupButtonPanel();
        GridPane textFieldPanel = setupTextFieldPanel();
        HBox radioButtonPanel = setupRadioButtonPanel();

        getChildren().addAll(backButtonAndEmployeePanel, employeeTableView);
        if (Controller.getCurrentEmployee().getPrivilege().getName().equals("admin")) {
            getChildren().addAll(buttonPanel, textFieldPanel, radioButtonPanel);
        }
    }

    private void setupTableView() {
        employeeObservableList = employeeController.loadEmployee();
        employeeTableView.setItems(employeeObservableList);

        TableColumn<Employee, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Employee, String> nameColumn = new TableColumn<>("Ime");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Employee, String> surnameColumn = new TableColumn<>("Prezime");
        surnameColumn.setMinWidth(200);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<Employee, Privilege> privilegeColumn = new TableColumn<>("Privilegija");
        privilegeColumn.setMinWidth(200);
        privilegeColumn.setCellValueFactory(new PropertyValueFactory<>("privilege"));

        employeeTableView.getColumns().addAll(idColumn, nameColumn, surnameColumn, privilegeColumn);
    }

    private HBox setupButtonPanel() {
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(addEmployeeButton, editEmployeeButton, deleteEmployeeButton, deleteCheckBox);
        backButton.setOnAction(this::onClickBackButton);
        addEmployeeButton.setOnAction(this::onClickAddEmployeeButton);
        editEmployeeButton.setOnAction(this::onCLickEditEmployeeButton);
        deleteEmployeeButton.setOnAction(this::onClickDeleteEmployeeButton);
        deleteEmployeeButton.setDisable(true);
        deleteCheckBox.setOnAction(this::onClickDeleteCheckBox);
        return hBox;
    }

    private GridPane setupTextFieldPanel() {
        GridPane gridPane = new GridPane();
        nameTextField.setPromptText("Enter name...");
        nameTextField.setMaxWidth(200);
        surnameTextField.setPromptText("Enter surname...");
        surnameTextField.setMaxWidth(200);
        usernameTextField.setPromptText("Enter username...");
        usernameTextField.setMaxWidth(200);
        passwordField.setPromptText("Enter password...");
        passwordField.setMaxWidth(200);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameTextField, 0, 1);
        gridPane.add(surnameLabel, 1, 0);
        gridPane.add(surnameTextField, 1, 1);
        gridPane.add(usernameLabel, 2, 0);
        gridPane.add(usernameTextField, 2, 1);
        gridPane.add(passwordLabel, 3, 0);
        gridPane.add(passwordField, 3, 1);
        return gridPane;
    }

    private HBox setupRadioButtonPanel() {
        adminRadioButton.setSelected(false);
        userRadioButton.setSelected(true);
        ToggleGroup toggleGroup = new ToggleGroup();
        adminRadioButton.setToggleGroup(toggleGroup);
        userRadioButton.setToggleGroup(toggleGroup);
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(adminRadioButton, userRadioButton);
        return hBox;
    }

    private void onCLickEditEmployeeButton(ActionEvent actionEvent) {
        if (employeeTableView.getSelectionModel().getSelectedItem() == null) {
            Dialog dialog = new Dialog<>();
            dialog.setTitle("Greška");
            dialog.setContentText("Selektujte zaposlenika kojeg želite editovati");
            dialog.show();
            dialog.setHeight(150);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        } else {
            Controller.setEditEmployee(employeeTableView.getSelectionModel().getSelectedItem());
            Stage stage = Controller.instance().getEditStage();
            stage.setTitle("Editovanje zaposlenika");
            EmployeeEditPanel employeeEditPanel = new EmployeeEditPanel();
            Scene scene = new Scene(employeeEditPanel);
            stage.setScene(scene);
            stage.show();
        }
    }

    private void onClickDeleteCheckBox(ActionEvent actionEvent) {
        if (deleteCheckBox.isSelected()) {
            deleteEmployeeButton.setDisable(false);
        } else {
            deleteEmployeeButton.setDisable(true);
        }
    }

    private void onClickDeleteEmployeeButton(ActionEvent actionEvent) {
        if (employeeTableView.getSelectionModel().getSelectedItem() == null) {
            Dialog dialog = new Dialog<>();
            dialog.setTitle("Greška");
            dialog.setContentText("Selektujte zaposlenika kojeg želite izbrisati");
            dialog.show();
            dialog.setHeight(150);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        } else {
            Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
            employeeObservableList.remove(selectedEmployee);

            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Controller.PU_NAME);
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Employee employee = entityManager.find(Employee.class, selectedEmployee.getId());
            entityManager.remove(employee);
            entityManager.getTransaction().commit();
        }
    }


    private void onClickBackButton(ActionEvent actionEvent) {
        StartPanel startPanel = new StartPanel();
        Scene scene = new Scene(startPanel);
        Controller.instance().getMainStage().setScene(scene);
        Controller.instance().getMainStage().setTitle("Pocetna");
    }


    private void onClickAddEmployeeButton(ActionEvent actionEvent) {
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
                if (query.getSingleResult() != null) {
                    Dialog dialog = new Dialog<>();
                    dialog.setTitle("Greška");
                    dialog.setContentText("Korisničko ime je zauzeto!");
                    dialog.show();
                    dialog.setHeight(150);
                    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                }
            } catch (NoResultException e) {
                if (passwordField.getText().length() < 6) {
                    Dialog dialog = new Dialog<>();
                    dialog.setTitle("Greška");
                    dialog.setContentText("Lozinka je prekratka (minimalno 6 karaktera)!");
                    dialog.show();
                    dialog.setHeight(150);
                    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                } else {
                    Employee employee = new Employee();
                    employee.setName(nameTextField.getText());
                    employee.setSurname(surnameTextField.getText());
                    employee.setUsername(usernameTextField.getText());
                    employee.setPassword(passwordField.getText());
                    if (adminRadioButton.isSelected()) {
                        Privilege privilege = entityManager.find(Privilege.class, 1);
                        employee.setPrivilege(privilege);
                    } else {
                        Privilege privilege = entityManager.find(Privilege.class, 2);
                        employee.setPrivilege(privilege);
                    }
                    entityManager.getTransaction().begin();
                    entityManager.persist(employee);
                    entityManager.getTransaction().commit();
                    employeeObservableList.add(employee);
                }
            }
        }
        clearTextField();
    }

    private void clearTextField() {
        nameTextField.clear();
        surnameTextField.clear();
        usernameTextField.clear();
        passwordField.clear();
    }
}

