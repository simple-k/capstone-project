package com.capstone.simplek.School;
import com.capstone.simplek.District.District;
import com.capstone.simplek.Service.Service;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "schools")
public class School {

    @Id @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "schools_services",
            joinColumns = {@JoinColumn(name = "school_id")},
            inverseJoinColumns = {@JoinColumn(name = "service_id")}
    )
    private List<Service> services;

    public School() {
    }

    public School(long id, String name, String address, District district, List<Service> services) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.district = district;
        this.services = services;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

}// class