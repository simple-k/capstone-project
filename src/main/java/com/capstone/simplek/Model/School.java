package com.capstone.simplek.Model;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "schools")
public class School {

    @Id @GeneratedValue
    private long id;

    @Column(nullable = false, length = 19)
    private String stateSchoolId;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @Column(nullable = false, length = 2)
    private String highGrade;

    @Column(nullable = false, length = 32)
    private String schoolName;

    @Column(nullable = false, length = 32)
    private String streetAddress;

    @Column(nullable = false, length = 5)
    private String zipCode;

    @Column(nullable = false, length = 14)
    private String phone;

    @Column
    private boolean charter;

    @Column(name = "title_i_school")
    private boolean titleISchool;

    @Column(name = "title_1_school_wide")
    private boolean title1SchoolWide;

    @Column(length = 10)
    private int students;

    @Column(length = 10)
    private int teachers;

    @Column
    private double studentTeacherRatio;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "schools_services",
            joinColumns = {@JoinColumn(name = "school_id")},
            inverseJoinColumns = {@JoinColumn(name = "service_id")}
    )
    private List<Service> services;

    public School() {
    }

    public School(long id, String stateSchoolId, District district, String highGrade, String schoolName, String streetAddress, String zipCode, String phone, boolean charter, boolean titleISchool, boolean title1SchoolWide, int students, int teachers, double studentTeacherRatio, List<Service> services) {
        this.id = id;
        this.stateSchoolId = stateSchoolId;
        this.district = district;
        this.highGrade = highGrade;
        this.schoolName = schoolName;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.phone = phone;
        this.charter = charter;
        this.titleISchool = titleISchool;
        this.title1SchoolWide = title1SchoolWide;
        this.students = students;
        this.teachers = teachers;
        this.studentTeacherRatio = studentTeacherRatio;
        this.services = services;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStateSchoolId() {
        return stateSchoolId;
    }

    public void setStateSchoolId(String stateSchoolId) {
        this.stateSchoolId = stateSchoolId;
    }

    public String getHighGrade() {
        return highGrade;
    }

    public void setHighGrade(String highGrade) {
        this.highGrade = highGrade;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isCharter() {
        return charter;
    }

    public void setCharter(boolean charter) {
        this.charter = charter;
    }

    public boolean isTitleISchool() {
        return titleISchool;
    }

    public void setTitleISchool(boolean titleISchool) {
        this.titleISchool = titleISchool;
    }

    public boolean isTitle1SchoolWide() {
        return title1SchoolWide;
    }

    public void setTitle1SchoolWide(boolean title1SchoolWide) {
        this.title1SchoolWide = title1SchoolWide;
    }

    public int getStudents() {
        return students;
    }

    public void setStudents(int students) {
        this.students = students;
    }

    public int getTeachers() {
        return teachers;
    }

    public void setTeachers(int teachers) {
        this.teachers = teachers;
    }

    public double getStudentTeacherRatio() {
        return studentTeacherRatio;
    }

    public void setStudentTeacherRatio(double studentTeacherRatio) {
        this.studentTeacherRatio = studentTeacherRatio;
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