package com.example.demo.calculator

import org.springframework.stereotype.Service

@Service
class CalculatorService {

    fun add(a: Int, b: Int): Int {
        return a + b
    }
}