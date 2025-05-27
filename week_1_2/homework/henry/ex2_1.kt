fun main() {
    val messyText = "     removed   이     문자의   공백   "

    // 이 부분을 함수 체이닝 형태로 바꿔볼 수 있다면 가산점!
//    val rules =
//        listOf(
//            ::trimAll,
//            ::removeExtraSpaces,
//            ::toSnakeCase,
//            ::capitalizeWords,
//        )
//
//    val cleaned = messyText.cleanUp(rules)

    val cleaned =
        messyText
            .trimAll()
            .removeExtraSpaces()
            .toSnakeCase()
            .capitalizeWords()

    println("원본: '$messyText'")
    println("변환 후: '$cleaned'")
}

fun clean(
    text: String,
    rules: List<(String) -> String>,
): String = rules.fold(text) { acc, rule -> rule(acc) }

fun String.cleanUp(rules: List<(String) -> String>): String = clean(this, rules)

fun removeExtraSpacesFun(text: String): String {
//    var cleanedText = text
//    while (true) {
//        val index = cleanedText.indexOf(Const.DOUBLE_WHITESPACE)
//        if (index == -1) {
//            return cleanedText
//        }
//        cleanedText = cleanedText.replace(Const.DOUBLE_WHITESPACE, Const.SINGLE_WHITESPACE)
//    }
    return text.replace(Const.DOUBLE_WHITESPACE_REGEX, Const.SINGLE_WHITESPACE)
}

fun trimAllFun(text: String): String = text.trim()

fun toSnakeCaseFun(text: String): String = text.replace(Const.SINGLE_WHITESPACE_REGEX, Const.UNDER_SCORE)

fun capitalizeWordsFun(text: String): String = text.replaceFirstChar { it.uppercase() }

// 메서드 체이닝 방식을 사용하기 위해
fun String.removeExtraSpaces(): String = removeExtraSpacesFun(this)

fun String.trimAll(): String = trimAllFun(this)

fun String.toSnakeCase(): String = toSnakeCaseFun(this)

fun String.capitalizeWords(): String = capitalizeWordsFun(this)

object Const {
    const val SINGLE_WHITESPACE = " "
    const val UNDER_SCORE = "_"
    const val SINGLE_WHITESPACE_REGEX_PATTERN = "\\s"
    const val DOUBLE_WHITESPACE_REGEX_PATTERN = "\\s{2,}"
    val SINGLE_WHITESPACE_REGEX = Regex(SINGLE_WHITESPACE_REGEX_PATTERN)
    val DOUBLE_WHITESPACE_REGEX = Regex(DOUBLE_WHITESPACE_REGEX_PATTERN)
}
