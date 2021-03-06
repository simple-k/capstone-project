package com.capstone.simplek.Model;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "children")
public class Children {

    @Id @GeneratedValue
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @Transient
    private String dobString;

    @Column
    private char gender;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "children_services",
            joinColumns = {@JoinColumn(name = "children_id")},
            inverseJoinColumns = {@JoinColumn(name = "service_id")}
    )
    private List<Services> services;

    public Children() {
    }
    public Children(long id, String firstName, String lastName, User user, List<Services> services) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.user = user;
        this.services = services;
    }
    public Children(long id, String firstName, String lastName, Date dob, String dobString, char gender, User user, List<Services> services) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.dobString = dobString;
        this.gender = gender;
        this.user = user;
        this.services = services;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getDobString() {
        return dobString;
    }

    public void setDobString(String dobString) {
        this.dobString = dobString;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }

}// class