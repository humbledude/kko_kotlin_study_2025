package com.example.demo.calculator

import io.mockk.every
import io.mockk.mockk
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import kotlin.test.Test


@WebMvcTest(CalculatorController::class)
@Import(CalculatorServiceMockConfig::class)
class CalculatorControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var calculatorService: CalculatorService

    @Test
    fun `1 이랑 2 랑 더해보자`() {
        every { calculatorService.add(1, 2) } returns 3

        mockMvc.get("/calculator/add") {
            param("a", "1")
            param("b", "2")
        }.andExpect {
            status { isOk() }
            content { string("3")}
        }
    }

    @Test
    fun `0으로 나눠보자`() {
        every { calculatorService.divide(1, 0)} throws ArithmeticException()

        mockMvc.get("/calculator/divide") {
            param("a", "1")
            param("b", "0")
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.message").value("error")
        }

    }
}

@TestConfiguration
class CalculatorServiceMockConfig {
    @Bean
    fun calculatorService(): CalculatorService = mockk()
}