package com.manunin.score.mapper;

import com.manunin.score.configuration.MapperConfiguration;
import com.manunin.score.dto.GroupStudentDto;
import com.manunin.score.model.GroupStudent;
import com.manunin.score.model.Student;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Mapper(config = MapperConfiguration.class)
public interface GroupStudentMapper {

    default GroupStudentDto toDto(GroupStudent groupStudent) {
        if (groupStudent == null) {
            return null;
        }
        GroupStudentDto groupStudentDto = new GroupStudentDto();
        groupStudentDto.setGroupId(groupStudent.getId().getGroupId());
        groupStudentDto.setStudentId(groupStudent.getId().getStudentId());
        groupStudentDto.setName(groupStudent.getStudent().getName());
        groupStudentDto.setPhone(groupStudent.getStudent().getPhone());
        groupStudentDto.setComment(groupStudent.getComment());
        groupStudentDto.setAmount(groupStudent.getAmount());
        groupStudentDto.setPaid(groupStudent.isPaid());
        return groupStudentDto;
    }

    List<GroupStudentDto> toDtoList(Set<GroupStudent> groupStudentList);

    default Student studentFromDto(GroupStudentDto groupStudentDto) {
        Student student = new Student();
        student.setId(groupStudentDto.getStudentId());
        student.setName(groupStudentDto.getName());
        student.setPhone(groupStudentDto.getPhone());
        return student;
    }

}
