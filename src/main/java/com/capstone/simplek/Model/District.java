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
    private String stateDistrictId;

    @Column
    private String image;

    @Column
    private String url;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "district")
    private List<School> schools = new ArrayList<>();

    public District() {
    }

    public District(long id, String name, String stateDistrictId, String image, String url, List<School> schools) {
        this.id = id;
        this.name = name;
        this.stateDistrictId = stateDistrictId;
        this.image = image;
        this.url = url;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<School> getSchools() {
        return schools;
    }

    public void setSchools(List<School> schools) {
        this.schools = schools;
    }

}// class