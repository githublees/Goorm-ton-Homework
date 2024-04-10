package goorm.responseerrormodel.api.service;

import goorm.responseerrormodel.db.entitiy.Student;
import goorm.responseerrormodel.db.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("studentService")
public class StudentServiceImpl implements StudentService{

    @Autowired
    StudentRepository studentRepository;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findAllByGrade(int grade) {
        return studentRepository.findByGrade(grade);
    }

    @Override
    public Student addStudent(Student student) {
        studentRepository.save(student);
        return student;
    }
}
