package com.imconsulting.company;

import com.imconsulting.UI.Controller;
import com.imconsulting.employee.Employee;
import com.imconsulting.employee.EmployeePanel;
import com.imconsulting.employee.privilege.Privilege;
import jakarta.persistence.*;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.lang.module.Configuration;

public class CompanyEditPanel extends GridPane {

    private final Label nameLabel = new Label("Naziv kompanije:");
    private final TextField nameTextField = new TextField();

    private final Label addressLabel = new Label("Adresa kompanije:");
    private final TextField addressTextField = new TextField();

    private final Label contactLabel = new Label("Kontakt kompanije");
    private final TextField contactTextField = new TextField();

    private final Button saveCompanyButton = new Button("Save");

    private Company company;

    public CompanyEditPanel() {
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(20));

        company = Controller.getEditCompany();

        //forma za editovanje
        nameTextField.setMaxWidth(200);
        nameTextField.setText(company.getName());

        addressTextField.setMaxWidth(200);
        addressTextField.setText(company.getAddress());

        contactTextField.setMaxWidth(200);
        contactTextField.setText(company.getContact());


        add(nameLabel, 0, 0);
        add(nameTextField, 1, 0);

        add(addressLabel, 0, 1);
        add(addressTextField, 1, 1);

        add(contactLabel, 0, 2);
        add(contactTextField, 1, 2);

        add(saveCompanyButton, 0, 3);


        saveCompanyButton.setOnAction(this::onCLickSaveCompanyButton);

    }

    private void onCLickSaveCompanyButton(ActionEvent actionEvent) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Controller.PU_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        Company editCompany = entityManager.find(Company.class, company.getId());
        editCompany.setName(nameTextField.getText());
        editCompany.setAddress(addressTextField.getText());
        editCompany.setContact(contactTextField.getText());
        entityManager.merge(editCompany);
        entityManager.getTransaction().commit();
        Scene scene = new Scene(new CompanyPanel());
        Controller.instance().getMainStage().setScene(scene);
        Controller.instance().getEditStage().close();

    }

}