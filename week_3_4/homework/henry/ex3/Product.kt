package ex3

data class Product(
    val name: String,
    val price: Int,
) {
    override fun toString(): String = "$name: ${price}원"

    companion object {
        fun parsePair(pair: Pair<String, Int>): Product =
            runCatching {
                Product(pair.first, pair.second)
            }.onFailure {
                print("잘못 입력한 것 같은데요?")
            }.getOrThrow()
    }
}
