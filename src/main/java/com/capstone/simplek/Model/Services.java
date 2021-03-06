package com.capstone.simplek.Model;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "services")
public class Services {

    @Id @GeneratedValue
    private long id;

    @Column
    private String service;

    @ManyToMany(mappedBy = "services")
    public List<Children> children;

    @ManyToMany(mappedBy = "services")
    public List<School> schools;

    public Services() {
    }

    public Services(long id, String service, List<Children> children, List<School> schools) {
        this.id = id;
        this.service = service;
        this.children = children;
        this.schools = schools;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public List<Children> getChildren() {
        return children;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }

    public List<School> getSchools() {
        return schools;
    }

    public void setSchools(List<School> schools) {
        this.schools = schools;
    }

}// class