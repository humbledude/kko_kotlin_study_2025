package exam1

import java.util.Locale

fun removeExtraSpaces(text: String) = text.replace(" {2,}".toRegex(), " ")
fun trimAll(text: String) = text.trim()
fun toSnakeCase(text: String) = text.replace(" ", "_").lowercase(Locale.getDefault())
fun capitalizeWords(text: String) = text.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
fun clean(text: String, rules: List<(String) -> String>): String {
    var value = text
    rules.map {
        value = it(value)
    }
    return value
}
fun String.cleanUp(rules: List<(String) -> String>) = clean(this, rules)

fun main() {
    val messyText = "     removed   이     문자의   공백   "
    // 이 부분을 함수 체이닝 형태로 바꿔볼 수 있다면 가산점!
    val rules = listOf(
        ::trimAll,
        ::removeExtraSpaces,
        ::capitalizeWords
    )
    println("원본: '$messyText'")
    val cleaned = clean(messyText, rules)
    println("변환: '$cleaned'")
    println("확장함수: ${messyText.cleanUp(rules)}")
}

