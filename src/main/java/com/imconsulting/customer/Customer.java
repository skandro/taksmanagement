package com.imconsulting.customer;

import com.imconsulting.action.Action;
import com.imconsulting.company.Company;
import com.imconsulting.employee.Employee;
import com.imconsulting.empstatus.EmpStatus;
import com.imconsulting.profession.Profession;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "customers", catalog = "project")
@NamedQueries(value = {
        @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
})
public class Customer implements Serializable {
    @Id
    @Column(name = "id", insertable = false, unique = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private int id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String surname;

    @Basic(optional = false)
    private LocalDate birthday;

    @Basic(optional = false)
    private String address;

    @Basic(optional = false)
    private String mobile;

    @Basic(optional = false)
    private String email;

    @JoinColumn(name = "id_empstatus", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EmpStatus empStatus;

    @JoinColumn(name = "id_profession", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Profession profession;

    @JoinColumn(name = "id_company", referencedColumnName = "id")
    @ManyToOne
    private Company company;

    @JoinColumn(name = "id_employee", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Employee employee;

    @Basic(optional = false)
    @Column(name = "date_registry")
    private LocalDate dateRegisty;

    @OneToMany(mappedBy = "customer")
    private List<Action> actionList;

    public Customer() {
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmpStatus getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(EmpStatus empStatus) {
        this.empStatus = empStatus;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getDateRegisty() {
        return dateRegisty;
    }

    public void setDateRegisty(LocalDate dateRegisty) {
        this.dateRegisty = dateRegisty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
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