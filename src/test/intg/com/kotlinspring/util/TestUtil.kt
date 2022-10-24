package com.kotlinspring.util

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.Course
import com.kotlinspring.entity.Instructor

fun courseEntityList(instructor: Instructor? = null) = listOf(
    Course(null,
        "Build RestFul APis using SpringBoot and Kotlin", "Development",
        instructor),
    Course(null,
        "Build Reactive Microservices using Spring WebFlux/SpringBoot", "Development"
        ,instructor
    ),
    Course(null,
        "Wiremock for Java Developers", "Development" ,
        instructor)
)

fun instructorEntity(name : String = "Dilip Sundarraj")
        = Instructor(null, name)

fun courseDTO(
    id: Int? = null,
    name: String = "Build RestFul APis using Spring Boot and Kotlin",
    category: String = "Philip Winnington",
) = CourseDTO(
    id,
    name,
    category
)