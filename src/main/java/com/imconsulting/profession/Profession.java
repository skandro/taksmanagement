package com.imconsulting.profession;

import com.imconsulting.customer.Customer;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "profession", catalog = "project")
@NamedQueries(value = {
        @NamedQuery(name = "Profession.findAll", query = "SELECT p FROM Profession p"),
})
public class Profession implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private int id;
    @Basic(optional = false)
    private String name;

    @OneToMany(mappedBy = "profession")
    private List<Customer> customerList;

    public Profession() {
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profession that = (Profession) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }
}