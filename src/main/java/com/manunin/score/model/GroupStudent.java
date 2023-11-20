package com.manunin.score.model;

import javax.persistence.*;

@Entity
@Table(name = "groups_students")
public class GroupStudent {
    @EmbeddedId
    private GroupStudentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupId")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    private Student student;

    private int amount;

    private String comment;

    private boolean paid;

    public GroupStudent(Group group, Student student, int amount, String comment, boolean paid) {
        this.group = group;
        this.student = student;
        this.amount = amount;
        this.comment = comment;
        this.paid = paid;
        this.id = new GroupStudentId(group.getId(), student.getId());
    }

    public GroupStudent() {
    }

    public GroupStudentId getId() {
        return id;
    }

    public void setId(GroupStudentId id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}

