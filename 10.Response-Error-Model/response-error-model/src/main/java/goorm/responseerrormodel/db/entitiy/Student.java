package goorm.responseerrormodel.db.entitiy;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Student {

    String name;
    Integer grade;

    public Student() {
    }

    public Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }
}
