package com.example.demo.calculator

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.assertThrows
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CalculatorControllerTest2 {

    @MockK
    lateinit var calculatorService: CalculatorService

    @InjectMockKs
    lateinit var controller: CalculatorController

    @BeforeTest
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `1이랑 2랑 더해볼께`() {
        every { calculatorService.add(1, 2)} returns 3

        assertEquals(3, controller.add(1, 2))
    }

    @Test
    fun `0으로 나눠복꼐`() {
        every { calculatorService.divide(1, 0)} throws ArithmeticException()

        assertFailsWith<ArithmeticException> {
            calculatorService.divide(1, 0)
        }
    }


}