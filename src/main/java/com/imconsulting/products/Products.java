package com.imconsulting.products;

import com.imconsulting.action.Action;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products", catalog = "project")
@NamedQueries({
        @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p")
})
public class Products implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private int id;
    @Basic(optional = false)
    private String name;
    @OneToMany(mappedBy = "products")
    private List<Action> actionList;

    public Products() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return id == products.id;
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
