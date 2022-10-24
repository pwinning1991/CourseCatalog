package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.dto.InstructorDTO
import com.kotlinspring.service.CourseService
import com.kotlinspring.service.InstructorService
import com.kotlinspring.util.courseDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [InstructorContoller::class])
@AutoConfigureWebTestClient
class InstructorControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var instructorServiceMockk : InstructorService

    @Test
    fun addCourse() {
        val instructorDTO = InstructorDTO(null,  "Phil")
        every { instructorServiceMockk.createInstructor(any()) } returns InstructorDTO(1, "Phil")

        val savedInstructorDTO = webTestClient.post()
            .uri("/v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDTO::class.java)
            .returnResult()
            .responseBody
        Assertions.assertTrue {
            savedInstructorDTO!!.id != null
        }
    }
}