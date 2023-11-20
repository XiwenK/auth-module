package com.manunin.score.repository;

import com.manunin.score.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByPhone(String phone);
    Boolean existsByPhone(String phone);

}
