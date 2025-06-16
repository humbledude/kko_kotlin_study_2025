package com.example.demo.sample

import kotlin.test.Test
import kotlin.test.assertEquals


class SampleTest {

    @Test
    fun testAddition() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `문법 공부중`() {
        val f: () -> Int = { 1 + 3 }
        assertEquals(4, f())

        val f2: String.() -> Int = { this.length + 3}
        assertEquals(7, f2("test"))
        assertEquals(7,  "test".f2())
    }




    class Form {
        private val elements = mutableListOf<String> ()

        fun input(name: String) {
            elements.add("Input: $name")
        }

        fun button(label: String) {
            elements.add("Button: $label")
        }

        fun render(): String {
            return elements.joinToString("\n")
        }
    }

    fun form(init: Form.() -> Unit): Form {
        val form = Form()
        form.init()
        return form
    }

    @Test
    fun `dsl공부중`() {
        val text = form {
            input ("username")
            input ("password")
            button("test")
        }.render()
        println(text)

        val form1 = Form()
        form1.input("username")
        form1.input("password")
        form1.button("test")
        val text1 = form1.render()
        println(text1)

    }
}