package com.imconsulting.company;

import com.imconsulting.UI.Controller;
import com.imconsulting.UI.paneli.StartPanel;
import com.imconsulting.employee.Employee;
import com.imconsulting.employee.EmployeeEditPanel;
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

public class CompanyPanel extends VBox {

    private final Label currentEmployeeLabel = new Label();
    private final Button backButton = new Button("Back");
    private final TableView<Company> companyTableView = new TableView<>();
    private CompanyController companyController = new CompanyController();

    private final TextField nameTextField = new TextField();
    private final Label nameLabel = new Label("Naziv firme: ");

    private final TextField addressTextField = new TextField();
    private final Label addressLabel = new Label("Adresa firme: ");

    private final TextField contactTextField = new TextField();
    private final Label ccontactLabel = new Label("Kontakt firme: ");

    private final Button addCompanyButton = new Button("Add Company");
    private final Button editCompanyButton = new Button("Edit Company");
    private final Button deleteCompanyButton = new Button("Delete Company");
    private final CheckBox deleteCheckBox = new CheckBox("Delete");


    public CompanyPanel() {
        setSpacing(10);
        setPadding(new Insets(10));

        currentEmployeeLabel.setText(Controller.getCurrentEmployee().getName() + ", " + Controller.getCurrentEmployee().getSurname());
        BorderPane borderPane = new BorderPane(null, null, currentEmployeeLabel, null, backButton);


        //TABELA
        ObservableList<Company> companyObservableList = companyController.loadCompany();
        companyTableView.setItems(companyObservableList);

        TableColumn<Company, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Company, String> nameColumn = new TableColumn<>("Ime");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Company, String> addressColumn = new TableColumn<>("Adresa");
        nameColumn.setMinWidth(200);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Company, String> contactColumn = new TableColumn<>("Kontakt");
        nameColumn.setMinWidth(200);
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        companyTableView.getColumns().addAll(idColumn, nameColumn, addressColumn, contactColumn);

        // kreiranje panela sa poljima

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(addCompanyButton, editCompanyButton, deleteCompanyButton, deleteCheckBox);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameTextField, 0, 1);
        nameTextField.setPromptText("Enter Company name:");

        gridPane.add(addressLabel, 1, 0);
        gridPane.add(addressTextField, 1, 1);
        addressTextField.setPromptText("Enter Company address:");

        gridPane.add(ccontactLabel, 2, 0);
        gridPane.add(contactTextField, 2, 1);
        contactTextField.setPromptText("Enter Company contact:");

        backButton.setOnAction(this::onClickBackButton);
        deleteCompanyButton.setOnAction(this::onClickDeleteCompanyButton);
        deleteCompanyButton.setDisable(true);
        deleteCheckBox.setOnAction(this::onClickDeleteCheckBox);
        editCompanyButton.setOnAction(this::onClickEditCompanyButton);
        addCompanyButton.setOnAction(this::onClickAddCompanyButton);


        getChildren().addAll(borderPane, companyTableView, hBox, gridPane);

    }

    private void onClickDeleteCheckBox(ActionEvent actionEvent) {
        if (deleteCheckBox.isSelected()) {
            deleteCompanyButton.setDisable(false);
        } else {
            deleteCompanyButton.setDisable(true);
        }
    }

    private void onClickDeleteCompanyButton(ActionEvent actionEvent) {
        if (companyTableView.getSelectionModel().getSelectedItem() == null) {
            Dialog dialog = new Dialog<>();
            dialog.setTitle("Greška");
            dialog.setContentText("Selektujte kompaniju koju želite izbrisati");
            dialog.show();
            dialog.setHeight(150);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        } else {
            Company selectedCompany = companyTableView.getSelectionModel().getSelectedItem();
            ObservableList<Company> companyObservableList = companyTableView.getItems();
            companyObservableList.remove(selectedCompany);

            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Controller.PU_NAME);
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Company company = entityManager.find(Company.class, selectedCompany.getId());
            entityManager.remove(company);
            entityManager.getTransaction().commit();
        }

    }

    private void onClickEditCompanyButton(ActionEvent actionEvent) {
        if (companyTableView.getSelectionModel().getSelectedItem() == null) {
            Dialog dialog = new Dialog<>();
            dialog.setTitle("Greška");
            dialog.setContentText("Selektujte kompaniju koju želite editovati");
            dialog.show();
            dialog.setHeight(150);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        } else {
            Controller.setEditCompany(companyTableView.getSelectionModel().getSelectedItem());
            Stage stage = Controller.instance().getEditStage();
            stage.setTitle("Editovanje kompanije");
            CompanyEditPanel companyEditPanel = new CompanyEditPanel();
            Scene scene = new Scene(companyEditPanel);
            stage.setScene(scene);
            stage.show();
        }

    }

    private void onClickAddCompanyButton(ActionEvent actionEvent) {

        if (nameTextField.getText().isEmpty() || addressTextField.getText().isEmpty() || contactTextField.getText().isEmpty()) {
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
                Query query = entityManager.createNamedQuery("Company.findByName");
                query.setParameter("name", nameTextField.getText());
                if (query.getSingleResult() != null) {
                    Dialog dialog = new Dialog<>();
                    dialog.setTitle("Greška");
                    dialog.setContentText("Firma već postoji");
                    dialog.show();
                    dialog.setHeight(150);
                    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                    nameTextField.setText("");
                    addressTextField.setText("");
                    contactTextField.setText("");
                    return;
                }
            } catch (NoResultException e) {
            }

            Company company = new Company();
            company.setName(nameTextField.getText());
            company.setAddress(addressTextField.getText());
            company.setContact(contactTextField.getText());


            entityManager.getTransaction().begin();
            entityManager.persist(company);
            entityManager.getTransaction().commit();

            ObservableList<Company> companyObservableList = companyTableView.getItems();
            companyObservableList.add(company);
        }
        nameTextField.setText("");
        addressTextField.setText("");
        contactTextField.setText("");


    }

    private void onClickBackButton(ActionEvent actionEvent) {
        StartPanel startPanel = new StartPanel();
        Scene scene = new Scene(startPanel);
        Controller.instance().getMainStage().setScene(scene);
        Controller.instance().getMainStage().setTitle("Pocetna");
    }


}