package com.advpro.profiling.tutorial.service;

import com.advpro.profiling.tutorial.model.Student;
import com.advpro.profiling.tutorial.model.StudentCourse;
import com.advpro.profiling.tutorial.repository.StudentCourseRepository;
import com.advpro.profiling.tutorial.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author muhammad.khadafi
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<StudentCourse> getAllStudentsWithCourses() {
        List<StudentCourse> studentCourses = studentCourseRepository.findAll();

        Map<Long, Student> student_id = new HashMap<>();
        List<Student> students = studentRepository.findAll();
        for (Student student : students) {
            student_id.put(student.getId(), student);
        }

        List<StudentCourse> result = new ArrayList<>();
        for (StudentCourse studentCourse : studentCourses) {
            Student student = student_id.get(studentCourse.getStudent().getId());
            StudentCourse newStudentCourse = new StudentCourse();
            newStudentCourse.setStudent(student);
            newStudentCourse.setCourse(studentCourse.getCourse());
            result.add(newStudentCourse);
        }
        return result;
    }

    public Optional<Student> findStudentWithHighestGpa() {
        return studentRepository.findFirstByOrderByGpaDesc();
    }

    public String joinStudentNames() {
        List<Student> students = studentRepository.findAll();
        StringBuilder result_Builder = new StringBuilder();

        for (Student student : students) {
            result_Builder.append(student.getName()).append(", ");
        }

        if (!result_Builder.isEmpty()) {
            result_Builder.delete(result_Builder.length() - 2, result_Builder.length());
        }
        return result_Builder.toString();
    }
}
