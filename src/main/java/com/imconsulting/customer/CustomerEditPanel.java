package com.imconsulting.customer;

import com.imconsulting.UI.Controller;
import com.imconsulting.company.Company;
import com.imconsulting.empstatus.EmpStatus;
import com.imconsulting.profession.Profession;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.List;

public class CustomerEditPanel extends GridPane {

    private final Label nameLabel = new Label("Name: ");
    private final TextField nameTextField = new TextField();

    private final Label surnameLabel = new Label("Surname: ");
    private final TextField surnameTextField = new TextField();

    private final Label birthdayLabel = new Label("Birthday: ");
    private final DatePicker birthdayPicker = new DatePicker();

    private final Label addressLabel = new Label("Adress: ");
    private final TextField addressTextField = new TextField();

    private final Label mobileLabel = new Label("Mobile: ");
    private final TextField mobileTextField = new TextField();

    private final Label emailLabel = new Label("Email: ");
    private final TextField emailTextField = new TextField();

    private final Label empStatusLabel = new Label("Status: ");
    private final ComboBox<EmpStatus> empStatusComboBox = new ComboBox<>();

    private final Label professionLabel = new Label("Profession: ");
    private final ComboBox<Profession> professionComboBox = new ComboBox<>();

    private final Label companyLabel = new Label("Company: ");
    private final ComboBox<Company> companyComboBox = new ComboBox<>();

    private final Button saveCustomerButton = new Button("SAVE");
    private Customer customer;

    public CustomerEditPanel() {
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(20));

        customer = Controller.getEditCustomer();

        //forma za editovanje
        nameTextField.setMaxWidth(200);
        nameTextField.setText(customer.getName());

        surnameTextField.setMaxWidth(200);
        surnameTextField.setText(customer.getSurname());

        addressTextField.setMaxWidth(200);
        addressTextField.setText(customer.getAddress());

        mobileTextField.setMaxWidth(200);
        mobileTextField.setText(customer.getMobile());

        emailTextField.setMaxWidth(200);
        emailTextField.setText(customer.getEmail());

        birthdayPicker.setValue(customer.getBirthday());

        companyComboBox.setMaxWidth(200);

        professionComboBox.setMaxWidth(200);

        empStatusComboBox.setMaxWidth(200);

        //UNOS CHECK BOXA

        ObservableList<EmpStatus> empStatusObservableList = empStatusComboBox.getItems();
        ObservableList<Profession> professionObservableList = professionComboBox.getItems();
        ObservableList<Company> companyObservableList = companyComboBox.getItems();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Controller.PU_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("EmpStatus.findAll");
        List<EmpStatus> empStatusList = query.getResultList();
        query = entityManager.createNamedQuery("Profession.findAll");
        List<Profession> professionList = query.getResultList();
        query = entityManager.createNamedQuery("Company.findAll");
        List<Company> companyList = query.getResultList();
        entityManager.getTransaction().commit();

        empStatusObservableList.addAll(empStatusList);
        empStatusComboBox.setOnAction(this::onCLickEmpStatusComboBox);

        professionObservableList.addAll(professionList);
        companyObservableList.addAll(companyList);

        empStatusComboBox.setValue(customer.getEmpStatus());
        professionComboBox.setValue(customer.getProfession());

        companyComboBox.setValue(customer.getCompany());

        add(nameLabel, 0, 0);
        add(nameTextField, 1, 0);
        add(surnameLabel, 0, 1);
        add(surnameTextField, 1, 1);
        add(addressLabel, 0, 2);
        add(addressTextField, 1, 2);
        add(mobileLabel, 0, 3);
        add(mobileTextField, 1, 3);
        add(emailLabel, 0, 4);
        add(emailTextField, 1, 4);
        add(birthdayLabel, 0, 5);
        add(birthdayPicker, 1, 5);
        add(empStatusLabel, 0, 6);
        add(empStatusComboBox, 1, 6);
        add(professionLabel, 0, 7);
        add(professionComboBox, 1, 7);
        add(companyLabel, 0, 8);
        add(companyComboBox, 1, 8);

        add(saveCustomerButton, 0, 9);
        saveCustomerButton.setOnAction(this::onClickSaveCustomerButton);

        /**        if (!empStatusComboBox.getValue().getName().equals("Zaposlen")) {
         companyComboBox.setDisable(true);
         } else {
         companyComboBox.setDisable(false);
         }
         *
         */



    }

    private void onCLickEmpStatusComboBox(ActionEvent actionEvent) {
        if (!empStatusComboBox.getValue().getName().equals("Zaposlen")) {
            companyComboBox.setDisable(true);
        } else {
            companyComboBox.setDisable(false);
        }
    }

    private void onClickSaveCustomerButton(ActionEvent actionEvent) {
        if (nameTextField.getText().isEmpty() || surnameTextField.getText().isEmpty() || birthdayPicker.getValue() == null ||
                addressTextField.getText().isEmpty() || mobileTextField.getText().isEmpty() || emailTextField.getText().isEmpty() ||
                empStatusComboBox.getValue() == null || professionComboBox.getValue() == null ||
                (empStatusComboBox.getValue().getName().equals("Zaposlen") && companyComboBox.getValue() == null)) {
            Dialog dialog = new Dialog<>();
            dialog.setTitle("Greška");
            dialog.setContentText("Niste popunili sva polja!");
            dialog.show();
            dialog.setHeight(150);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        } else {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Controller.PU_NAME);
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Customer editCustomer = entityManager.find(Customer.class, customer.getId());

            editCustomer.setName(nameTextField.getText());
            editCustomer.setSurname(surnameTextField.getText());
            editCustomer.setBirthday(birthdayPicker.getValue());
            editCustomer.setAddress(addressTextField.getText());
            editCustomer.setMobile(mobileTextField.getText());
            editCustomer.setEmail(emailTextField.getText());
            editCustomer.setEmpStatus(empStatusComboBox.getValue());
            editCustomer.setProfession(professionComboBox.getValue());
            if (companyComboBox.getValue() != null) {
                editCustomer.setCompany(companyComboBox.getValue());
            }

            entityManager.getTransaction().begin();
            entityManager.merge(editCustomer);
            entityManager.getTransaction().commit();

            Scene scene = new Scene(new CustomerPanel());
            Controller.instance().getMainStage().setScene(scene);
            Controller.instance().getEditStage().close();
        }
    }
}
