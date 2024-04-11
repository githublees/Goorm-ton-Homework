package goorm.responseerrormodel.api.controller;

import goorm.responseerrormodel.api.response.ApiResponse;
import goorm.responseerrormodel.api.service.StudentService;
import goorm.responseerrormodel.common.exception.CustomException;
import goorm.responseerrormodel.common.exception.ErrorCode;
import goorm.responseerrormodel.common.exception.InputRestriction;
import goorm.responseerrormodel.db.entitiy.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    private static final int maxGrade = 6;

    @PostMapping("/student")
    public ApiResponse add(@RequestBody Student student) {

        if(student.getGrade() == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "grade 에 아무런 값이 입력되지 않았습니다.");
        }
        if(student.getGrade() >= maxGrade) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "grade 는 6이상 입력 할 수 없습니다.",  new InputRestriction(maxGrade));
        }

        return makeResponse(studentService.addStudent(student));
    }

    @GetMapping("/students")
    public ApiResponse getStudents() {
        return makeResponse(studentService.findAll());
    }

    @GetMapping("/students/{grade}")
    public ApiResponse getAllByGrade(@PathVariable int grade) {
        return makeResponse(studentService.findAllByGrade(grade));
    }

    public <T> ApiResponse<T> makeResponse(List<T> result) {
        return new ApiResponse<>(result);
    }

    public <T> ApiResponse<T> makeResponse(T result) {
        return new ApiResponse<>(result);
    }
}
