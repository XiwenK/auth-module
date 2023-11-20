package com.manunin.score.mapper;

import com.manunin.score.configuration.MapperConfiguration;
import com.manunin.score.dto.StudentDto;
import com.manunin.score.model.GroupStudent;
import com.manunin.score.model.Student;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(config = MapperConfiguration.class)
public interface StudentMapper {

    StudentDto toDto(Student student);

    default StudentDto toDto(GroupStudent groupStudent){
        StudentDto studentDto = new StudentDto();
        studentDto.setId(groupStudent.getStudent().getId());
        studentDto.setName(groupStudent.getStudent().getName());
        studentDto.setPhone(groupStudent.getStudent().getPhone());
        studentDto.setAmount(groupStudent.getAmount());
        studentDto.setComment(groupStudent.getComment());
        return studentDto;
    }

    Student fromDto(StudentDto student);

    List<StudentDto> toDtoList(List<Student> studentList);

}
