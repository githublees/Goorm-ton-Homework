package goorm.responseerrormodel.db.repository;

import goorm.responseerrormodel.db.entitiy.Student;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class StudentRepository {

    private static final Set<Student> students = new HashSet<>();

    public void save(Student student) {
        students.add(student);
    }

    public List<Student> findAll() {
        return students.stream()
                .sorted(Comparator.comparing(Student::getGrade))
                .collect(Collectors.toList());
    }

    public List<Student> findByGrade(int grade) {
        return students.stream()
                .filter(student -> student.getGrade() == grade)
                .collect(Collectors.toList());
    }
}
