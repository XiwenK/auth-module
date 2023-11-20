package com.manunin.score.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private LocalDateTime dateTime;

    private int placesCount;

    private int booked;

    private String url;

    private Integer placePrice;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @OneToMany(mappedBy = "group",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<GroupStudent> students = new HashSet<>();

    public Group() {
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getPlacesCount() {
        return placesCount;
    }

    public void setPlacesCount(int placesCount) {
        this.placesCount = placesCount;
    }

    public int getBooked() {
        return booked;
    }

    public void setBooked(int booked) {
        this.booked = booked;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule scheduleId) {
        this.schedule = scheduleId;
    }

    public Set<GroupStudent> getStudents() {
        return students;
    }

    public void setStudents(Set<GroupStudent> groupStudents) {
        this.students = groupStudents;
    }

    public void addStudent(Student student, int amount, String comment, boolean paid) {
        GroupStudent groupStudent = new GroupStudent(this, student, amount, comment, paid);
        students.add(groupStudent);
        System.out.println(student.getGroups());
    }

    public void updateStudent(Student student, int amount, String comment, boolean paid) {
        if (removeStudentById(student.getId())){
            GroupStudent groupStudent = new GroupStudent(this, student, amount, comment, paid);
            students.add(groupStudent);
        }
    }

    public boolean removeStudentById(Long studentId) {
        return students.removeIf(groupStudent -> groupStudent.getStudent().getId() == studentId);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Integer getPlacePrice() {
        return placePrice;
    }

    public void setPlacePrice(Integer placePrice) {
        this.placePrice = placePrice;
    }
}
