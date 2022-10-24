package com.kotlinspring.entity

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table


@Entity
@Table(name="INSTRUCTORS")
data class Instructor(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int ?,
    var name: String,
    @OneToMany(
        mappedBy = "instructor",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var courses: List<Course> = mutableListOf()
)