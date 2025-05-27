
fun clean(text: String, rules: List<(String) -> String>): String {
    var buff = text
    for (rule in rules) {
        buff = rule(buff)
    }

    return buff
}

fun String.cleanUp(rules: List<(String) -> String>): String {
    return clean(this, rules)
}


fun removeExtraSpaces(text: String): String {
    val buff = Regex("[ ]+").replace(text, " ")
    println("removeExtraSpaces, [$text] -> [$buff]")

    return buff
}

fun trimAll(text: String): String {
    val buff = text.trim()
    println("trimAll, [$text] -> [$buff]")
    return buff
}

fun toSnakeCase(text: String): String {
    val buff = Regex("[ ]").replace(text, "_")
    println("toSnakeCase, [$text] -> [$buff]")
    return buff
}

fun capitalizeWords(text: String): String {
    val buff = text.split(" ").map {it -> it.replaceFirstChar({
        if (it.isLowerCase()) {
            it.titlecase()
        } else {
            it.toString()
        }
    })
            
    } .joinToString(" ")
    println("capitalizeWords, [$text] -> [$buff]")
    return buff
}



fun main() {
    val messyText = "     removed   이     문자의   공백   "

    // 이 부분을 함수 체이닝 형태로 바꿔볼 수 있다면 가산점!
    val rules = listOf(
        ::trimAll,
        ::removeExtraSpaces,
        ::capitalizeWords,
        ::toSnakeCase
    )

    val cleaned = messyText.cleanUp(rules)

    println("원본: '$messyText'")
    println("변환 후: '$cleaned'")
}