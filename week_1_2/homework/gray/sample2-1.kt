// 고차함수
fun clean(text: String, rules: List<(String) -> String>): String
{
    var cleanedText = text
    for (rule in rules) {
        cleanedText = rule(cleanedText)
    }
    return cleanedText
}

// String 확장함수
fun String.cleanUp(rules: List<(String) -> String>): String = clean(this, rules)

fun trimAll(text: String): String {
    return text.trim()
}

fun removeExtraSpaces(text: String): String {
    return text.replace("\\s+".toRegex(), " ")
}

// replaceFirstChar를 사용해서 각 단어의 첫 문자를 대문자로
fun capitalizeWords(text: String): String {
    return text.split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { it.uppercase() }
    }
}

fun main() {
    val messyText = "     removed   이     문자의   공백   "

    // 이 부분을 함수 체이닝 형태로 바꿔볼 수 있다면 가산점!
    val rules = listOf(
        ::trimAll,
        ::removeExtraSpaces,
        ::capitalizeWords
    )

    val cleaned = messyText.cleanUp(rules)

    println("원본: '$messyText'")
    println("변환 후: '$cleaned'")
}