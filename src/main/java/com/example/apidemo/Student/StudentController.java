package com.example.apidemo.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/Student")
public class StudentController {
private final StudentService studentService;
@Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping
    public ResponseEntity<List<Student>> getStudent(){
        return ResponseEntity.ok(studentService.getStudent());
    }
    @PostMapping
    public ResponseEntity<String> createStudent(@RequestBody Student student){
        try{studentService.createStudent(student);
        return ResponseEntity.ok("Student created successfully");
        }catch(Exception e){return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email existed");}
    }
    @DeleteMapping(path="{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") long id){
        try{studentService.deleteStudent(id);
            return ResponseEntity.ok("Student deleted successfully");
        }catch(Exception e){return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find student with matched id");}
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") long id,
                                                 @RequestBody Student updatedStudent) {
        try {
            Student student = studentService.updateStudent(id, updatedStudent.getName(), updatedStudent.getEmail());
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }}