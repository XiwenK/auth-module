package com.manunin.score.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GroupStudentId implements Serializable {

    private static final long serialVersionUID = 118255822629907119L;

    @Column(name = "group_id")
    private long groupId;

    @Column(name = "student_id")
    private long studentId;

    public GroupStudentId() {
    }

    public GroupStudentId(Long groupId, Long studentId) {
        this.groupId = groupId;
        this.studentId = studentId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupStudentId)) return false;
        GroupStudentId that = (GroupStudentId) o;
        return getGroupId().equals(that.getGroupId()) &&
                getStudentId().equals(that.getStudentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGroupId(), getStudentId());
    }
}

