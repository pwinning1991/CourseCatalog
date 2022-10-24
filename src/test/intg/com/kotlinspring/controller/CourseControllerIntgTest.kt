package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.Course
import com.kotlinspring.repository.CourseRepository
import com.kotlinspring.repository.InstructorRepository
import com.kotlinspring.util.courseEntityList
import com.kotlinspring.util.instructorEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntgTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    internal fun setUp() {
        courseRepository.deleteAll()
        instructorRepository.deleteAll()
        val instructor =  instructorEntity()
        instructorRepository.save(instructor)
        val courses = courseEntityList(instructor)
        courseRepository.saveAll(courses)
    }

    @Test
    fun addCourse() {
        val instructor = instructorRepository.findAll().first()
        val courseDTO = CourseDTO(null, "Build Restful APIs using Springboot and Kotlin", "Phil", instructor.id)
        val savedCourseDTO = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody
        Assertions.assertTrue {
            savedCourseDTO!!.id != null
        }
    }

    @Test
    fun retrieveAllCourses(){
        val courseDTOs = webTestClient.get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody
        println("Course DTOs: $courseDTOs")
        Assertions.assertEquals(3, courseDTOs!!.size)
    }
    @Test
    fun retrieveAllCoursesByName(){
        val uri = UriComponentsBuilder.fromUriString("/v1/courses")
            .queryParam("course_name", "SpringBoot")
            .toUriString()
        val courseDTOs = webTestClient.get()
            .uri(uri)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody
        println("Course DTOs: $courseDTOs")
        Assertions.assertEquals(2, courseDTOs!!.size)
    }

    @Test
    fun updateCourse(){
        val instructor = instructorRepository.findAll().first()
        //existing course
        val course = Course(null, "Build RestFul APis using Springboot and Kotlin", "Development", instructor)
        courseRepository.save(course)
        // courseID
        //updated CourseDTO
        val updatedCourseDTO = CourseDTO(null, "Build RestFul APis using Springboot and Kotlin1", "Development", course.instructor!!.id)
        val updatedCourse = webTestClient
            .put()
            .uri("/v1/courses/{courseId}", course.id)
            .bodyValue(updatedCourseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody
        Assertions.assertEquals("Build RestFul APis using Springboot and Kotlin1", updatedCourse!!.name)
    }

    @Test
    fun deleteCourse(){
        val instructor = instructorRepository.findAll().first()
        val course = Course(null, "Build RestFul APis using Springboot and Kotlin", "Development", instructor)
        courseRepository.save(course)

        val updatedCourse = webTestClient
            .delete()
            .uri("/v1/courses/{courseId}", course.id)
            .exchange()
            .expectStatus().isNoContent
    }
}