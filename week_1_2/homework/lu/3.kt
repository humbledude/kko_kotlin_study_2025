package com.kakao.map.week2

import java.util.*

fun main() {
    val products: List<Map<String, Any>> = listOf(
        mapOf("name" to "Keyboard", "price" to 50000),
        mapOf("name" to "Egg", "price" to 5000),
        mapOf("name" to "PineApple", "price" to 15000),
        mapOf("name" to "Pizza", "price" to 15000),
        mapOf("name" to "Bread", "price" to 3000),
        mapOf("name" to "Onion", "price" to 1200),
        mapOf("name" to "Milk", "price" to 3000),
    )

    val scanner = Scanner(System.`in`)
    val cart = mutableListOf<Map<String, Any>>()
    println("### 상품 목록")
    products.mapIndexed { index, product ->
        product.also {
            println("${index+1} . ${it["name"]}: ${it["price"]}")
        }
    }

    while (true) {
        println("\n상품 번호를 추가해주세요. 그만 하시려면 (n을 입력하세요)")
        val input = scanner.nextLine()

        if (input.toIntOrNull() == null) {
            break
        }
        if (products.size < input.toInt() || input.toInt() <= 0) {
            println("상품 항목 수를 넘겨서 입력할 수 없습니다.")
            continue
        }

        val product = products[input.toInt()-1]
        val addProduct = mutableMapOf<String, Any>().apply {
            put("name", product["name"]!!)
            put("price", product["price"]!!)
        }

        cart.add(addProduct).also {
            println("상품이 장바구니에 추가 되었습니다. ${product["name"]}: ${product["price"]}")
        }

    }

    // 장바구니 내용 출력
    println("\n최종 장바구니 목록")

    cart.forEach { item ->
        item.let {
            println("${it["name"]}: ${it["price"]}원")
        }
    }

    cart.sumOf { it["price"] as Int }.let { total ->
        when (total >= 100000) {
            true -> {
                println("총 금액 ${total}원 - 할인 적용 대상입니다!")
            }
            false -> {
                println("총 금액 ${total}원")
            }
        }
    }

    cart.filter { (it["price"] as Int) <= 10000 }.let { cheapItems ->
        println("\n10,000원 이하 상품 목록:")
        cheapItems.forEach { println("${it["name"]}: ${it["price"]}원") }
    }
}
