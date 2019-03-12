package com.capstone.simplek.User;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="address")
    private String address;

    @Column(name="zip_code")
    private String zipCode;

    @Column(name="phone_number", length = 14)
    private String phoneNumber;

    @Column(name="is_admin")
    private boolean isAdmin;

    public User(long id, String username, String password, String email, String firstName, String lastName, String address, String zipCode, String phoneNumber, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
    }

    public User(){};

}// class