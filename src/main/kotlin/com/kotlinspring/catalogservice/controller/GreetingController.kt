package com.kotlinspring.catalogservice.controller

import com.kotlinspring.catalogservice.service.GreetingsService
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/greetings")
class GreetingController(val greetingsService: GreetingsService) {

    companion object : KLogging()

    @GetMapping("/{name}")
    fun retrieveGreeting(@PathVariable("name") name: String): String{
        //return "Hello $name"
        logger.info("name is $name")
        return greetingsService.retrieveGreeting(name)

    }
}