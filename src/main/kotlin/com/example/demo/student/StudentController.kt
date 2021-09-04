package com.example.demo.student

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.function.Predicate
import java.util.function.Supplier


@RestController
@RequestMapping("api/v1/students")
class StudentController {
    companion object {
        private val STUDENTS = listOf(
            Student(1, "Alice"),
            Student(2, "Bob"),
            Student(3, "Carol")
        )
    }

    @GetMapping(path = ["{studentId}"])
    fun getStudent(@PathVariable("studentId") studentId: Int): Student {
        return STUDENTS.find { it.studentId == studentId} ?: throw IllegalStateException("Student $studentId does not exist.")
    }
}
