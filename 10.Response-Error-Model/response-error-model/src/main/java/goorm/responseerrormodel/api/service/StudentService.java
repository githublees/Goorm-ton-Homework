package goorm.responseerrormodel.api.service;

import goorm.responseerrormodel.db.entitiy.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    List<Student> findAll();
    List<Student> findAllByGrade(int grade);
    Student addStudent(Student student);

}
