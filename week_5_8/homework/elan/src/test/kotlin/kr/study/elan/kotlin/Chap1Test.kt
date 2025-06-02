package kr.study.elan.kotlin

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class Chap1Test {
    val calculator = Calculator()

    @Test
    fun `한글로 테스트가 된다`() {
        println("테스트")
    }

    @Test
    fun `3 더하기 4는 7이다`() {
        assertThat(calculator.add(3, 4))
            .isEqualTo(7)
    }

    @Test
    fun `10 나누기 5는 2이다`() {
        assertThat(calculator.div(10, 5))
            .isEqualTo(2)
    }

    @Test
    fun `0으로 나누면 오류가 난다`() {
        assertThatThrownBy {
            calculator.div(10, 0)
        }.isInstanceOf(ArithmeticException::class.java)
    }

    class Calculator {
        fun add(x: Int, y: Int): Int = x + y
        fun div(x: Int, y: Int): Int {
            if (y == 0) {
                throw ArithmeticException("0으로 나눌수 없습니다.")
            }
            return x / y
        }
    }
}