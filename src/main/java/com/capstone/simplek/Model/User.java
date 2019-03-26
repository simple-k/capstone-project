package com.capstone.simplek.Model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    public User(User copy) {
        id = copy.id;
        email = copy.email;
        username = copy.username;
        password = copy.password;
        isAdmin = copy.isAdmin;
        uFirstName = copy.uFirstName;
        uLastName = copy.uLastName;
        address = copy.address;
        zipCode = copy.zipCode;
        phoneNumber = copy.phoneNumber;
        children = copy.children;
    }

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name="first_name")
    private String uFirstName;

    @Column(name="last_name")
    private String uLastName;

    @Column(name="address")
    private String address = "";

    @Column(name="zip_code", length = 5)
    private String zipCode = "";

    @Column(name="phone_number", length = 14)
    private String phoneNumber = "";

    @Column(name="is_admin", length = 5)
    private boolean isAdmin;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Children> children = new ArrayList<>();

    public User() {
    }

    public User(long id, String username, String password, String confirmPassword, String email, String uFirstName, String uLastName, String address, String zipCode, String phoneNumber, boolean isAdmin, List<Children> children) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.uFirstName = uFirstName;
        this.uLastName = uLastName;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
        this.children = children;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuFirstName() {
        return uFirstName;
    }

    public void setuFirstName(String uFirstName) {
        this.uFirstName = uFirstName;
    }

    public String getuLastName() {
        return uLastName;
    }

    public void setuLastName(String uLastName) {
        this.uLastName = uLastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Children> getChildren() {
        return children;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }

}// class
