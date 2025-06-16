fun main() {

    // 상품 이름과 가격이 저장되어 있다.
    val productList = listOf(
        mapOf("name" to "Keyboard", "price" to 50000),
        mapOf("name" to "Egg", "price" to 5000),
        mapOf("name" to "PineApple", "price" to 15000),
        mapOf("name" to "Pizza", "price" to 15000),
        mapOf("name" to "Bread", "price" to 3000),
        mapOf("name" to "Onion", "price" to 1200),
        mapOf("name" to "Milk", "price" to 3000),
    )

    // 상품 목록 출력
    fun printProductList() {
        productList.forEachIndexed {
           index, element -> println("${index + 1} . ${element["name"]}: ${element["price"]}")
        }
        println()
    }

    val shoppingCart = mutableListOf<Int>()

    // 장바구니에 상품을 추가할 때 추가 직전 상품 이름과 가격을 출력한다.
    fun addProduct(productNum: Int) {
        val productIdx = productNum - 1
        val product = productList.getOrNull(productIdx)
        product?.let {
            shoppingCart.add(productIdx)
            println("상품이 장바구니에 추가 되었습니다. ${it["name"]}: ${it["price"]}")
        }
    }

    // 상품을 장바구니에 추가한 뒤 최종 장바구니 상태를 출력한다.
    fun getAllShoppingPrice(): Int {
        val priceList: List<Int> = shoppingCart
            .map { num -> productList[num] }
            .map { product -> product["price"] as Int }

        return priceList.sum()
    }

    // 특정 가격 이하인 상품은 별도로 필터링 해서 보여준다.
    fun filterLowPriceProduct(lowPrice: Int) {
        println()

        shoppingCart
            .map { num -> productList[num] }
            .filter { product -> product["price"] as Int <= lowPrice }
            .forEach { product -> println("${product["name"]}: ${product["price"]}원") }
    }

    fun printResult() {
        println("최종 장바구니 목록")
        shoppingCart.forEach {
            val product = productList[it]
            println("${product["name"]}: ${product["price"]}원")
        }

        // 총 가격이 10만원 이상이면 "할인 적용" 메시지를 출력한다.
        val allPrice = getAllShoppingPrice()
        val discountMsg = if (allPrice >= 100000) "- 할인 적용 대상입니다!" else ""

        println("총 금액 $allPrice $discountMsg")

        filterLowPriceProduct(10000)
    }

    printProductList()
    while (true) {
        println("상품 번호를 추가해주세요. 그만 하시려면 (n을 입력하세요)")
        val input = readlnOrNull()

        input?.let {
            when {
                input == "n" -> {
                    printResult()
                    return
                }
                else -> {
                    addProduct(input.toInt())
                }
            }
        }
    }

}
