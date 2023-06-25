package com.imconsulting.customer;

import com.imconsulting.UI.Controller;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    public ObservableList<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Controller.PU_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("Customer.findAll");
        customers = query.getResultList();
        entityManager.getTransaction().commit();

        ObservableList<Customer> customerObservableList = FXCollections.observableList(customers);
        return customerObservableList;

    }
}
