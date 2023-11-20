package com.manunin.score.repository;

import com.manunin.score.model.GroupStudent;
import com.manunin.score.model.GroupStudentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GroupStudentRepository extends JpaRepository<GroupStudent, GroupStudentId> {

    boolean existsByGroupIdAndStudentId(Long groupId, Long studentId);

    GroupStudent findByGroupIdAndStudentId(Long groupId, Long studentId);

    Set<GroupStudent> findByGroupId(Long groupId);

    void deleteByGroupIdAndStudentId(Long groupId, Long studentId);
}
