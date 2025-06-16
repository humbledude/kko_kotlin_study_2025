#!/usr/bin/env kscript

val products = listOf(
    mapOf("name" to "Keyboard", "price" to 50000),
    mapOf("name" to "Egg", "price" to 5000),
    mapOf("name" to "PineApple", "price" to 15000),
    mapOf("name" to "Pizza", "price" to 15000),
    mapOf("name" to "Bread", "price" to 3000),
    mapOf("name" to "Onion", "price" to 1200),
    mapOf("name" to "Milk", "price" to 3000)
)

val cart = mutableListOf<Map<String, Any>>()

println("### 상품 목록")
products.forEachIndexed { i, product ->
    println("${i + 1} . ${product["name"]}: ${product["price"]}")
}

while (true) {
    println("\n상품 번호를 추가해주세요. 그만 하시려면 (n을 입력하세요)")
    val input = readln()

    if (input == "n") break

    input.toIntOrNull()?.let { i ->
        products.getOrNull(i - 1)?.also { product ->
            println("상품이 장바구니에 추가 되었습니다. ${product["name"]}: ${product["price"]}")
            cart.add(product)
        }
    } ?: println("1~${products.size} 사이의 상품 번호를 입력하세요.")
}

println("\n최종 장바구니 목록")
cart.forEach {
    println("${it["name"]}: ${it["price"]}원")
}

val totalPrice = cart.sumOf { it["price"] as Int }
val discountMessage = if (totalPrice >= 100000) " - 할인 적용 대상입니다!" else ""

println("총 금액 ${totalPrice}원 ${discountMessage}")

println("\n10,000원 이하 상품 목록:")
cart.filter {
    it["price"] as Int <= 10_000
}.forEach {
    println("${it["name"]}: ${it["price"]}원")
}
