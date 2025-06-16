import java.text.DecimalFormat
import java.util.*

fun mainEx3() {
    println("[ 장보기 시작 ]\n")

    println("### 상품 목록 ###")
    productList.forEach(::println)

    val scanner = Scanner(System.`in`)
    var inputStr = ""
    while (inputStr != "n") {
        println("\n상품 번호를 추가해주세요. 그만 하시려면 n을 입력하세요.")
        inputStr = scanner.next()
        when {
            inputStr.toIntOrNull() != null -> addToCart(inputStr.toInt()) // 숫자 변환 실패하면 null 됨
            inputStr == "n" -> printCartInfo() // 종료했으니 카트 정보 프린트
            else -> println("상품 번호는 숫자입니다. ex) 1") // 숫자/n 외에 다른거 입력했을때 문구
        }
    }

    scanner.close()
}

// 기본 상품 정보
val originProduct = listOf(
    mapOf("name" to "Keyboard", "price" to 50000),
    mapOf("name" to "Egg", "price" to 5000),
    mapOf("name" to "PineApple", "price" to 15000),
    mapOf("name" to "Pizza", "price" to 15000),
    mapOf("name" to "Bread", "price" to 3000),
    mapOf("name" to "Onion", "price" to 1200),
    mapOf("name" to "Milk", "price" to 3000),
)

// 기본 상품 정보를 id 를 가진 productList 로 만들어봄
data class Product(val id: Int, val name: String, val price: Int) {
    val prettyPrice: String = DecimalFormat("#,###").format(price)
    override fun toString(): String {
        return "$id. $name: ${prettyPrice}원"
    }
}

val productList: List<Product> = originProduct.mapIndexed { idx, product ->
    Product(id = idx+1, name = product["name"] as String, price = product["price"] as Int)
}

// 카트 정보
val cart : MutableList<Product> = mutableListOf()

fun addToCart (productId: Int) {
    val product: Product? = productList.filter { it.id == productId }.getOrNull(0)
    product?.let {
        cart.add(product) // 카트에 상품 추가
        println("상품이 장바구니에 추가 되었습니다. ${product.name}: ${product.prettyPrice}") // 추가된 정보 출력
    }
}

fun printCartInfo() {
    println("\n[ 장보기 종료 ]")

    when(cart.size) {
        0 -> println("장바구니가 비어있습니다.")
        else -> {
            println("\n### 최종 장바구니 목록 ###")
            cart.forEach(::println)

            val totalPrice = cart.sumOf { it.price }
            println("총 금액 " + DecimalFormat("#,###").format(totalPrice) + "원" + if (totalPrice >= 100000) " - 할인 적용 대상입니다!" else "")

            println("\n### 10,000원 이하 상품 목록 ###")
            cart.filter { it.price <= 10000 } .forEach(::println)
        }
    }
}