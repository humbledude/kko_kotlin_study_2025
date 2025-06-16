package ex3

data class Market(
    private val products: Map<Int, Product>,
) {
    override fun toString(): String =
        products
            .map { "${it.key}. ${it.value.name}: ${it.value.price}" }
            .joinToString(
                separator = "\n",
                prefix = "### 상품 목록\n",
                postfix = "\n",
            )

    fun pick(index: Int): Product {
        val get = products[index]
        if (get == null || index !in 1..products.size) throw IllegalArgumentException("상품이 1 ~ ${products.size}까지 있는데용?")
        return get
    }

    fun pickTo(
        index: Int,
        cart: Cart,
    ) {
        val product = pick(index)
        cart.add(product)
    }

    companion object {
        fun stockProducts(productPairs: List<Pair<String, Int>>): Market =
            productPairs
                .mapIndexed { index, pair ->
                    index + 1 to Product.parsePair(pair)
                }.toMap()
                .let(::Market)
    }
}
