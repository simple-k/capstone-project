package com.capstone.simplek.Model;
import com.capstone.simplek.Model.School;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "districts")
public class District {

    @Id @GeneratedValue
    private long id;

    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "district")
    private List<School> schools = new ArrayList<>();

    public District() {
    }

    public District(long id, String name, List<School> schools) {
        this.id = id;
        this.name = name;
        this.schools = schools;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<School> getSchools() {
        return schools;
    }

    public void setSchools(List<School> schools) {
        this.schools = schools;
    }

}// class