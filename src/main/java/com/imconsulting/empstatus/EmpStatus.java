package com.imconsulting.empstatus;

import com.imconsulting.customer.Customer;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "emp_status", catalog = "project")
@NamedQueries(value = {
        @NamedQuery(name = "EmpStatus.findAll", query = "SELECT e FROM EmpStatus e"),
})
public class EmpStatus implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private int id;
    @Basic(optional = false)
    private String name;

    @OneToMany(mappedBy = "empStatus")
    private List<Customer> customerList;

    public EmpStatus() {
    }


    public List<Customer> getEmpStatusList() {
        return customerList;
    }

    public void setEmpStatusList(List<Customer> customerList) {
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
        EmpStatus empStatus = (EmpStatus) o;
        return id == empStatus.id;
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