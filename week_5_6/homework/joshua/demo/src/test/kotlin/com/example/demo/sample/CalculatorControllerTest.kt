package com.example.demo.sample

import com.example.demo.calculator.CalculatorController
import com.example.demo.calculator.CalculatorService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get


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
}

@TestConfiguration
class CalculatorServiceMockConfig {
    @Bean
    fun calculatorService(): CalculatorService = mockk()
}