package com.capstone.simplek.Model;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "schools")
public class School {

    @Id @GeneratedValue
    private long id;

    @Column(nullable = false, length = 150)
    private String stateSchoolId;

    @Column(nullable = false, length = 10)
    private String lowGrade;

    @Column(nullable = false, length = 10)
    private String highGrade;

    @Column(nullable = false, length = 150)
    private String schoolName;

    @Column(nullable = false)
    private String streetAddress;

    @Column(nullable = false, length = 5)
    private String zipCode;

    @Column(nullable = false, length = 14)
    private String phone;

    @Column(nullable = false, length = 3)
    private String charter;

    @Column(nullable = false, length = 3)
    private String magnet;

    @Column(name = "title_i_school", nullable = false, length = 3)
    private String titleISchool;

    @Column(name = "title_1_school_wide", nullable = false, length = 3)
    private String title1SchoolWide;

    @Column(nullable = false, length = 10)
    private String students;

    @Column(nullable = false, length = 10)
    private String teachers;

    @Column(nullable = false, length = 10)
    private String studentTeacherRatio;

    @Column(nullable = false, length = 10)
    private String freeLunch;

    @Column(nullable = false, length = 10)
    private String reducedLunch;

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

    public School(long id, String stateSchoolId, String lowGrade, String highGrade, String schoolName, String streetAddress, String zipCode, String phone, String charter, String magnet, String titleISchool, String title1SchoolWide, String students, String teachers, String studentTeacherRatio, String freeLunch, String reducedLunch, District district, List<Service> services) {
        this.id = id;
        this.stateSchoolId = stateSchoolId;
        this.lowGrade = lowGrade;
        this.highGrade = highGrade;
        this.schoolName = schoolName;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.phone = phone;
        this.charter = charter;
        this.magnet = magnet;
        this.titleISchool = titleISchool;
        this.title1SchoolWide = title1SchoolWide;
        this.students = students;
        this.teachers = teachers;
        this.studentTeacherRatio = studentTeacherRatio;
        this.freeLunch = freeLunch;
        this.reducedLunch = reducedLunch;
        this.district = district;
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

    public String getLowGrade() {
        return lowGrade;
    }

    public void setLowGrade(String lowGrade) {
        this.lowGrade = lowGrade;
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

    public String isCharter() {
        return charter;
    }

    public void setCharter(String charter) {
        this.charter = charter;
    }

    public String isMagnet() {
        return magnet;
    }

    public void setMagnet(String magnet) {
        this.magnet = magnet;
    }

    public String isTitleISchool() {
        return titleISchool;
    }

    public void setTitleISchool(String titleISchool) {
        this.titleISchool = titleISchool;
    }

    public String isTitle1SchoolWide() {
        return title1SchoolWide;
    }

    public void setTitle1SchoolWide(String title1SchoolWide) {
        this.title1SchoolWide = title1SchoolWide;
    }

    public String getStudents() {
        return students;
    }

    public void setStudents(String students) {
        this.students = students;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public String getStudentTeacherRatio() {
        return studentTeacherRatio;
    }

    public void setStudentTeacherRatio(String studentTeacherRatio) {
        this.studentTeacherRatio = studentTeacherRatio;
    }

    public String getFreeLunch() {
        return freeLunch;
    }

    public void setFreeLunch(String freeLunch) {
        this.freeLunch = freeLunch;
    }

    public String getReducedLunch() {
        return reducedLunch;
    }

    public void setReducedLunch(String reducedLunch) {
        this.reducedLunch = reducedLunch;
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