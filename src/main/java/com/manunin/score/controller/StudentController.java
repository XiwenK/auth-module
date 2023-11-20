package com.manunin.score.controller;

import com.manunin.score.dto.GroupStudentDto;
import com.manunin.score.dto.MessageResponse;
import com.manunin.score.exception.ResultType;
import com.manunin.score.mapper.GroupStudentMapper;
import com.manunin.score.mapper.StudentMapper;
import com.manunin.score.model.Group;
import com.manunin.score.model.GroupStudent;
import com.manunin.score.model.Student;
import com.manunin.score.service.GroupService;
import com.manunin.score.service.GroupStudentService;
import com.manunin.score.service.StudentService;
import com.manunin.score.utils.RestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    private final GroupService groupService;

    private final GroupStudentService groupStudentService;

    private final StudentMapper mapper;

    private final GroupStudentMapper groupStudentMapper;

    private Pattern pattern = Pattern.compile("^[\\p{L}0-9\\p{Punct}()+\\-\\s]*$");

    @Autowired
    public StudentController(StudentService studentService, GroupService groupService, GroupStudentService groupStudentService, StudentMapper mapper, GroupStudentMapper groupStudentMapper) {
        this.studentService = studentService;
        this.groupService = groupService;
        this.groupStudentService = groupStudentService;
        this.mapper = mapper;
        this.groupStudentMapper = groupStudentMapper;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Get Students list")
    @GetMapping
    private ResponseEntity<List<GroupStudentDto>> getStudents(@RequestParam String groupId) {
        if (!StringUtils.isBlank(groupId)) {
            Set<GroupStudent> students = groupService.findById(Long.parseLong(groupId)).getStudents();
            return ResponseEntity.ok(groupStudentMapper.toDtoList(students));
        }
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Students count is loaded"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Get students count")
    @GetMapping("/studentsCount")
    public ResponseEntity<Integer> getStudentsCount(@RequestParam String string) {
        return ResponseEntity.ok(groupService.findById(Long.parseLong(string)).getStudents().size());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student successfully added to group"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Add student")
    @PostMapping
    private ResponseEntity<MessageResponse> addStudentToGroup(@Valid @RequestBody GroupStudentDto groupStudent) {
        if (groupService.findStudentInGroup(groupStudent.getGroupId(), groupStudent.getStudentId())) {
            return RestUtils.response(ResultType.STUDENT_WITH_PHONE_AND_NUMBER_ALREADY_ADDED);
        }
        Group group = groupService.findById(groupStudent.getGroupId());
        if (group == null) {
            return RestUtils.response(ResultType.GROUP_NOT_FOUND);
        }

        Student student;
        if (!studentService.existsByPhone(groupStudent.getPhone())) {
            studentService.addStudent(groupStudentMapper.studentFromDto(groupStudent));
            student = studentService.findByPhone(groupStudent.getPhone());
        } else {
            student = studentService.findByPhone(groupStudent.getPhone());
            if (group.getStudents().stream().anyMatch(s -> s.getStudent().getId() == student.getId())) {
                return RestUtils.response(ResultType.STUDENT_WITH_PHONE_AND_NUMBER_ALREADY_ADDED);
            }
        }

        group.addStudent(student, groupStudent.getAmount(), groupStudent.getComment(), groupStudent.isPaid());
        groupService.updateGroup(group);

        return RestUtils.response(ResultType.GROUP_STUDENT_SUCCESSFULLY_CREATED);
    }

    //todo add privilege restrictions
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student successfully added to group"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Add student by short url")
    @PostMapping("/addByUrl")
    private ResponseEntity<MessageResponse> addStudentToGroupByShortUrl(@Valid @RequestBody GroupStudentDto groupStudent) {
        if (!pattern.matcher(groupStudent.getName()).matches()
            || !pattern.matcher(groupStudent.getPhone()).matches()){
            return RestUtils.response(ResultType.BAD_REQUEST);
        }

        if (groupStudent.getComment() !=null && !pattern.matcher(groupStudent.getComment()).matches()){
            return RestUtils.response(ResultType.BAD_REQUEST);
        }

        Optional<String> groupIdByUrl = groupService.getGroupIdByUrl(groupStudent.getUrl());
        if (groupIdByUrl.isEmpty()){
            return RestUtils.response(ResultType.GROUP_NOT_FOUND);
        }
        groupStudent.setGroupId(Long.parseLong(groupIdByUrl.get()));
        return addStudentToGroup(groupStudent);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Update student")
    @PutMapping
    private ResponseEntity<MessageResponse> updateGroup(@Valid @RequestBody GroupStudentDto groupStudent) {
        if (!groupService.findStudentInGroup(groupStudent.getGroupId(), groupStudent.getStudentId())) {
            return RestUtils.response(ResultType.GROUP_STUDENT_NOT_FOUND);
        }

        Group group = groupService.findById(groupStudent.getGroupId());
        if (group == null) {
            return RestUtils.response(ResultType.GROUP_NOT_FOUND);
        }

        Student student = studentService.updateStudent(groupStudentMapper.studentFromDto(groupStudent));
        group.updateStudent(student, groupStudent.getAmount(), groupStudent.getComment(), groupStudent.isPaid());
        groupService.updateGroup(group);

        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @Operation(summary = "Delete student from group")
    @DeleteMapping
    private ResponseEntity<MessageResponse> deleteStudent(@RequestParam("id") final Long id, @RequestParam("groupId") final Long groupId) {
        Group group = groupService.findById(groupId);
        if (group == null) {
            return RestUtils.response(ResultType.GROUP_NOT_FOUND);
        }

        Student student = studentService.findById(id);
        if (student == null) {
            return RestUtils.response(ResultType.STUDENT_NOT_FOUND);
        }

        group.removeStudentById(id);
        groupService.updateGroup(group);

        return ResponseEntity.ok().build();
    }
}
