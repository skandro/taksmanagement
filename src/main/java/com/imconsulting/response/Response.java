package com.imconsulting.response;

import com.imconsulting.action.Action;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "responses", catalog = "project")
@NamedQueries(value = {
        @NamedQuery(name = "Response.findAll", query = "SELECT r FROM Response r")
})
public class Response implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private int id;

    @Column(name = "name")
    @Basic(optional = false)
    private String name;
    @OneToMany(mappedBy = "response")
    private List<Action> actionList;

    public Response() {
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
        Response response = (Response) o;
        return id == response.id;
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