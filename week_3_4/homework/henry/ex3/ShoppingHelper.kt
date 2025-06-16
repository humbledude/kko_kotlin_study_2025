package ex3

class ShoppingHelper private constructor(
    val market: Market,
    val cart: Cart,
) {
    fun start() {
        println(market)
        while (true) {
            println("상품 번호를 추가해주세요. 그만 하시려면 (n을 입력하세요)")
            val userInput = readlnOrNull() ?: throw IllegalArgumentException("뭘 입력하신거죠?")
            if (userInput == "n") {
                break
            }
            market.pickTo(userInput.toInt(), cart)
            println()
        }
        println(cart)
        println()
        println(cart.filter(10000))
    }

    companion object {
        fun registerMarket(market: Market): ShoppingHelper = ShoppingHelper(market, Cart.init())
    }
}
