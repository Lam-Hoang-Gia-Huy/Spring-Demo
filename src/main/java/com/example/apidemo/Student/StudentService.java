package com.example.apidemo.Student;

import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public List<Student> getStudent(){
        return studentRepository.findAll();
    }
    public Student createStudent(Student student){
        Optional<Student> Student = studentRepository.findStudentByEmail(student.getEmail());
        if(Student.isPresent()){
            throw new IllegalStateException("Email already");
        }
        return studentRepository.save(student);
    }
    public void deleteStudent(long id){
        boolean exist=studentRepository.existsById(id);
        if(!exist){
            throw new IllegalStateException("Student not found");
        }
        studentRepository.deleteById(id);
    }
    @Transactional
    public Student updateStudent(long id, String name, String email) {
        // Find the student by ID
        Optional<Student> optionalStudent = studentRepository.findById(id);

        // Check if the student exists
        if (optionalStudent.isEmpty()) {
            throw new IllegalArgumentException("Student with ID " + id + " not found");
        }
        Student student = optionalStudent.get();
        if (name != null && !name.isEmpty() && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }
        if (email != null && !email.isEmpty() && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalArgumentException("Email taken");
            }
            student.setEmail(email);
        }
        return studentRepository.save(student);
    }
}
