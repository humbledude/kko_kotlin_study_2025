package ex3

fun main() {
    val product: List<Pair<String, Int>> =
        listOf(
            mapOf("name" to "Keyboard", "price" to 50000),
            mapOf("name" to "Egg", "price" to 5000),
            mapOf("name" to "PineApple", "price" to 15000),
            mapOf("name" to "Pizza", "price" to 15000),
            mapOf("name" to "Bread", "price" to 3000),
            mapOf("name" to "Onion", "price" to 1200),
            mapOf("name" to "Milk", "price" to 3000),
        ).map { map ->
            (map["name"] as String) to (map["price"] as Int)
        }

    val market = Market.stockProducts(product)
    val helper = ShoppingHelper.registerMarket(market)
    helper.start()
}

/**
 * - 상품에 번호 붙여야 함.
 * - 상품 목록 출력 기능
 * - 상품 장바구니 추가 기능
 * - 장바구니 상태 출력 기능
 * - 할인 적용 여부 판단 기능
 * - 특정 금액 이하 필터링 기능
 */
