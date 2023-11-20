package com.manunin.score.service;

import com.manunin.score.model.GroupStudent;
import com.manunin.score.repository.GroupStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class GroupStudentService {

    private final GroupStudentRepository groupStudentRepository;

    @Autowired
    public GroupStudentService(GroupStudentRepository groupStudentRepository) {
        this.groupStudentRepository = groupStudentRepository;
    }

    public boolean existsByGroupIdAndStudentId(final Long groupId, final Long studentId) {
        return groupStudentRepository.existsByGroupIdAndStudentId(groupId, studentId);
    }

    public void addStudentToGroup(final GroupStudent groupStudent) {
        groupStudentRepository.save(groupStudent);
    }

    public void updateStudentInGroup(final GroupStudent groupStudent) {
        groupStudentRepository.save(groupStudent);
    }

    public GroupStudent findByGroupIdAndStudentId(final Long groupId, final Long studentId) {
        return groupStudentRepository.findByGroupIdAndStudentId(groupId, studentId);
    }

    public Set<GroupStudent> findByGroupId(final Long groupId) {
        return groupStudentRepository.findByGroupId(groupId);
    }

    public void deleteByGroupIdAndStudentId(final Long groupId, final Long studentId) {
        groupStudentRepository.deleteByGroupIdAndStudentId(groupId, studentId);
    }

}
