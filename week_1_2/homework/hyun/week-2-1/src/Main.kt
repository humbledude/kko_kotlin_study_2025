fun main() {
    val messyText = "     removed   이     문자의   공백   "

    val cleaned = messyText
        .cleanUp(::trimAll)
        .cleanUp(::removeExtraSpaces)
        .cleanUp(::capitalizeWords)

    println("원본: '$messyText'")
    println("변환 후: '$cleaned'")
}
