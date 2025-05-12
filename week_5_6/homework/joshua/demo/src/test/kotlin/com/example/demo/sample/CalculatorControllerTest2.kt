package com.example.demo.sample

import com.example.demo.calculator.CalculatorController
import com.example.demo.calculator.CalculatorService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CalculatorControllerTest2 {

    @MockK
    lateinit var calculatorService: CalculatorService

    @InjectMockKs
    lateinit var controller: CalculatorController

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `1이랑 2랑 더해볼께`() {
        every { calculatorService.add(1, 2)} returns 3

        assertThat(controller.add(1, 2)).isEqualTo(3)
    }
}