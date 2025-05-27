package week1

object TextCleaner {
    fun clean(text: String, rules: List<(String) -> String>): String {
        return rules.fold(text) { acc, rule -> rule(acc) }
    }
}

fun main() {
    val messyText = "     removed   이     문자의   공백   "

    // 이 부분을 함수 체이닝 형태로 바꿔볼 수 있다면 가산점!
    val rules = listOf(
        ::trimAll,
        ::removeExtraSpaces,
        ::toSnakeCase,
        ::capitalizeWords
    )

    val cleaned = messyText.cleanUp(rules)

    println("원본: '$messyText'")
    println("변환 후: '$cleaned'")
}

fun String.cleanUp(rules: List<(String) -> String>): String {
    return TextCleaner.clean(this, rules)
}

fun trimAll(text: String): String {
    return text.trimStart().trimEnd();
}

fun removeExtraSpaces(text: String): String {
    var prev : Char= 'z';
    var result : String = "";
    for (i in text){
        if (i.isWhitespace() && prev.isWhitespace()){
            continue;
        }
        result += i;
        prev = i
    }
    return result;
}

fun toSnakeCase(text: String): String {
    return text.replace(" ", "_");
}

fun capitalizeWords(text: String): String {
    return text
        .split("_")
        .joinToString("_") { it.replaceFirstChar { c -> c.uppercaseChar() } }
}