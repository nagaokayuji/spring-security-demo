package com.example.demo.security

enum class ApplicationUserRole(permissions: Set<ApplicationUserPermission>) {
    STUDENT(setOf()),
    ADMIN(
        setOf(
            ApplicationUserPermission.COURSE_READ,
            ApplicationUserPermission.COURSE_WRITE,
            ApplicationUserPermission.STUDENT_READ,
            ApplicationUserPermission.STUDENT_WRITE
        )
    ),
    ADMIN_TRAINEE(
        setOf(
            ApplicationUserPermission.COURSE_READ,
            ApplicationUserPermission.STUDENT_READ
        )
    );
}
