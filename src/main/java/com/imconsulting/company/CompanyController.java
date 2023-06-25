package com.imconsulting.company;

import com.imconsulting.UI.Controller;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class CompanyController {

    public ObservableList<Company> loadCompany() {
        List<Company> companies = new ArrayList<>();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Controller.PU_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("Company.findAll");
        companies = query.getResultList();
        entityManager.getTransaction().commit();

        ObservableList<Company> companyObservableList = FXCollections.observableList(companies);
        return companyObservableList;

    }
}