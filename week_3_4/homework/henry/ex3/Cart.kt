package ex3

class Cart private constructor(
    val carts: MutableList<Product>,
) {
    override fun toString(): String =
        carts.joinToString(
            separator = "\n",
            prefix = "최종 장바구니 목록\n",
            postfix = "\n" + checkout(),
        ) { it.toString() }

    fun checkout(): String {
        val totalPrice = carts.sumOf { it.price }
        var resultString = "총 금액 ${totalPrice}원"
        if (totalPrice >= 100000) resultString += " - 할인 적용 대상입니다!"
        return resultString
    }

    fun filter(filterPrice: Int): String =
        carts
            .filter { it.price <= filterPrice }
            .joinToString(
                separator = "\n",
                prefix = "${filterPrice}원 이하 상품 목록:\n",
            )

    fun add(product: Product) {
        carts.addLast(product)
        println("상품이 장바구니에 추가 되었습니다. $product")
    }

    companion object {
        fun init(): Cart = Cart(mutableListOf())
    }
}
