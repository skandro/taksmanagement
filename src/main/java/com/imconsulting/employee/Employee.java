package com.imconsulting.employee;

import com.imconsulting.action.Action;
import com.imconsulting.customer.Customer;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.imconsulting.employee.privilege.Privilege;
import jakarta.persistence.*;

@Entity
@Table(name = "employees", catalog = "project")
@NamedQueries(value = {
        @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
        @NamedQuery(name = "Employee.findByUsername", query = "SELECT e FROM Employee e WHERE e.username = :username")
})
public class Employee implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private int id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String surname;

    @Basic(optional = false)
    private String username;
    @Basic(optional = false)
    private String password;

    @OneToMany(mappedBy = "employee")
    private List<Action> actionList;

    @OneToMany(mappedBy = "employee")
    private List<Customer> customerList;
    @JoinColumn(name = "id_privilege", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Privilege privilege;

    public Employee() {
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public List<Action> getActionList() {
        return actionList;
    }

    public void setActionList(List<Action> actionList) {
        this.actionList = actionList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + ", " + surname;
    }
}