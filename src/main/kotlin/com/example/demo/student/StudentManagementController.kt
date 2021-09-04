package com.example.demo.student

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("management/api/students")
class StudentManagementController {

    companion object {
        private val STUDENTS = listOf(
            Student(1, "Alice"),
            Student(2, "Bob"),
            Student(3, "Carol")
        )
    }

    @GetMapping
    fun getAllStudents(): List<Student>? {
        println("getAllStudents")
        return STUDENTS
    }

    @PostMapping
    fun registerNewStudent(@RequestBody student: Student?) {
        println("registerNewStudent")
        println(student)
    }

    @DeleteMapping(path = ["{studentId}"])
    fun deleteStudent(@PathVariable("studentId") studentId: Int?) {
        println("deleteStudent")
        println(studentId)
    }

    @PutMapping(path = ["{studentId}"])
    fun updateStudent(@PathVariable("studentId") studentId: Int?, @RequestBody student: Student?) {
        println("updateStudent")
        println(String.format("%s %s", studentId, student))
    }
}

