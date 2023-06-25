package com.imconsulting.employee;

import com.imconsulting.UI.Controller;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class EmployeeController {

    public ObservableList<Employee> loadEmployee() {
        List<Employee> employees = new ArrayList<>();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Controller.PU_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("Employee.findAll");
        employees = query.getResultList();
        entityManager.getTransaction().commit();

        ObservableList<Employee> employeeObservableList = FXCollections.observableList(employees);
        return employeeObservableList;

    }

}
