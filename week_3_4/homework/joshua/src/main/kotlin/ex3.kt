data class Product(val name: String, val price: Int)

val allProducts = listOf(
    mapOf("name" to "Keyboard", "price" to 50000),
    mapOf("name" to "Egg", "price" to 5000),
    mapOf("name" to "PineApple", "price" to 15000),
    mapOf("name" to "Pizza", "price" to 15000),
    mapOf("name" to "Bread", "price" to 3000),
    mapOf("name" to "Onion", "price" to 1200),
    mapOf("name" to "Milk", "price" to 3000),
).run {
    this.map { Product(it["name"] as String, it["price"] as Int)}
        .mapIndexed { idx, it -> idx + 1 to it }
        .toMap()
}

val cart : MutableList<Product> = mutableListOf()


fun printProducts(products: Map<Int, Product>) {
    println("### 상품 목록")
    products.forEach { id, product -> println("$id. ${product.name}: ${product.price}")}
}

fun readInput(products: Map<Int, Product>) {
    var loop = true
    while(loop) {
        println()
        println("상품 번호를 추가해주세요. 그만 하시려면(n을 입력하세요)")
        val input = readLine()
        val id = input?.toIntOrNull()
        when {
            input == "n" -> loop = false
            id != null -> {
                products[id]?.let {
                    addToCart(it)
                }
            }
        }
    }
}

fun addToCart(product: Product) {
    cart.add(product)
    println("상품이 장바구니에 추가 되었습니다. ${product.name}: ${product.price}")
}

fun printProduct(product: Product) {
    println("${product.name}: ${product.price}원")
}

fun printCart() {
    println("최종 장바구니 목록")
    cart.forEach(::printProduct)
    cart.map {it.price}
        .sum()
        .run {
            val isDiscountable = this >= 100000
            val msg = "총 금액 ${this}원" + if (isDiscountable) " - 할인 적용 대상입니다!" else ""
            println(msg)
        }
}

fun printProductsUnderPrice(price: Int) {
    println("${price}원 이하 상품 목록:")
    cart.filter {it.price <= price}
        .forEach(::printProduct)
}

fun mainEx3() {

    printProducts(allProducts)
    readInput(allProducts)

    println()
    printCart()

    println()
    printProductsUnderPrice(10000)
}