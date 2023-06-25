package com.imconsulting.company;

import com.imconsulting.customer.Customer;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "companies", catalog = "project") // companies je naziv tabele u bazi, project je ime baze
@NamedQueries(value = {
        @NamedQuery(name = "Company.findAll", query = "SELECT c FROM Company c"),
})
public class Company implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private int id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String address;

    @Basic(optional = false)
    private String contact;

    @OneToMany(mappedBy = "company")
    private List<Customer> customerList;

    public Company() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id == company.id;
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
