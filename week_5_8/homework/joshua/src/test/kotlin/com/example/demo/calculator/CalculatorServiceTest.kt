package com.example.demo.calculator

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CalculatorServiceTest {

    var service = CalculatorService()

    @Test
    fun `일반 나누기`() {
        assertEquals(3, service.divide(6, 2))
    }

    @Test
    fun `0으로 나누기`() {
        assertFailsWith<ArithmeticException> {
            service.divide(6, 0)
        }
    }
}