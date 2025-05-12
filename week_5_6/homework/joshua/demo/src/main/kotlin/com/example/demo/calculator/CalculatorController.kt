package com.example.demo.calculator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/calculator")
class CalculatorController {

    @Autowired
    lateinit var calculatorService: CalculatorService

    @GetMapping("/add")
    fun add(@RequestParam a: Int, @RequestParam b: Int) : Int {
        return calculatorService.add(a, b)
    }
}