package com.manunin.score.service;

import com.manunin.score.model.Student;
import com.manunin.score.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student findByPhone(final String phone) {
        return studentRepository.findByPhone(phone).orElse(null);
    }

    public Student findById(final Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public void addStudent(final Student student) {
        studentRepository.save(student);
    }

    public boolean existsByPhone(final String phone) {
        return studentRepository.existsByPhone(phone);
    }

    public Student updateStudent(final Student student) {
        return studentRepository.save(student);
    }

}
