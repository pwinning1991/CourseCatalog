package com.kotlinspring.exception

import java.lang.RuntimeException


class InstructorNotValidException(s :String) : RuntimeException(s) {
}