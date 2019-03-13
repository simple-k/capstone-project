package com.capstone.simplek.Model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "districts")
public class District {

    @Id @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private  String stateDistrictId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "district")
    private List<School> schools = new ArrayList<>();

    public District() {
    }

    public District(long id, String name, String stateDistrictId, List<School> schools) {
        this.id = id;
        this.name = name;
        this.stateDistrictId = stateDistrictId;
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

    public String getStateDistrictId() {
        return stateDistrictId;
    }

    public void setStateDistrictId(String stateDistrictId) {
        this.stateDistrictId = stateDistrictId;
    }

    public List<School> getSchools() {
        return schools;
    }

    public void setSchools(List<School> schools) {
        this.schools = schools;
    }

}// class