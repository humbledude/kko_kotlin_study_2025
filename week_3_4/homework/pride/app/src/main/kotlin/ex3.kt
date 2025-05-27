package app.src

// 1. 상품 목록 출력
fun displayProductList(products: List<Map<String, Any>>) {
    println("### 상품 목록")
    products.forEachIndexed { index, item ->
        println("${index + 1}. ${item["name"]} : ${item["price"]}원")
    }
}

// 2. 사용자 입력 받고 장바구니에 담기
fun promptAndAddToCart(
    products: List<Map<String, Any>>,
    cart: MutableList<Map<String, Any>>
) {
    while (true) {
        println("\n상품 번호를 추가해주세요. 그만 하시려면 (n을 입력하세요)")
        val input = readlnOrNull() ?: break
        if (input.equals("n", ignoreCase = true)) break

        input.toIntOrNull()?.let { index ->
            cart.add(products[index - 1])
            val item = products[index - 1]
            println("상품이 장바구니에 추가 되었습니다. ${item["name"]} : ${item["price"]}")
        }
    }
}

// 3. 장바구니 요약 출력
fun printCartSummary(cart: List<Map<String, Any>>) {
    val total = cart.sumOf { it["price"] as Int }
    println("\n최종 장바구니 목록")
    cart.forEach { item ->
        println("${item["name"]} : ${item["price"]}원")
    }
    println("총 금액 ${total}원${if (total >= 100_000) " - 할인 적용 대상입니다!" else ""}")
}

// 4. 가격 필터 결과 출력
fun printFilteredCart(cart: List<Map<String, Any>>) {
    println("\n10,000원 이하 상품 목록:")
    cart.filter { (it["price"] as Int) <= 10_000 }
        .forEach { item ->
            println("${item["name"]} : ${item["price"]}원")
        }
}

fun main() {
    val product = listOf(
        mapOf("name" to "Keyboard", "price" to 50000),
        mapOf("name" to "Egg", "price" to 5000),
        mapOf("name" to "PineApple", "price" to 15000),
        mapOf("name" to "Pizza", "price" to 15000),
        mapOf("name" to "Bread", "price" to 3000),
        mapOf("name" to "Onion", "price" to 1200),
        mapOf("name" to "Milk", "price" to 3000),
    )
    val cart = mutableListOf<Map<String, Any>>()

    displayProductList(product)
    promptAndAddToCart(product, cart)
    printCartSummary(cart)
    printFilteredCart(cart)
}