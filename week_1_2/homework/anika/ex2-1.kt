// 연속된 공백을 하나로 줄이기
fun String.removeExtraSpaces(): String {
    return replace(Regex("\\s+"), " ")
}

// 앞 뒤 공백 제거
fun String.trimAll(): String {
    return trim()
}

// 띄어쓰기를 _ 로 바꾸고 모두 소문자 변환
fun String.toSnakeCase(): String {
    return replace(" ", "_").lowercase()
}

// 각 단어의 첫글자를 대문자로
fun String.capitalizeWords(): String {
    val capitalizedWords = mutableListOf<String>()

    for (word in split(" ")) {
        if (word.isNotEmpty()) {
            capitalizedWords.add(word[0].uppercase() + word.substring(1))
        }
    }

    return capitalizedWords.joinToString(" ")
}

fun String.cleanUp(rules: List<(String) -> String>): String {
    var cleaned = this

    for (rule in rules) {
        cleaned = rule(cleaned)
    }

    return cleaned
}

fun main() {
    val messyText = "     removed   이     문자의   공백   "

    // 이 부분을 함수 체이닝 형태로 바꿔볼 수 있다면 가산점!
//    val rules = listOf(
//        ::trimAll,
//        ::removeExtraSpaces,
//        ::capitalizeWords
//    )

    val cleaned = messyText.trimAll()
        .removeExtraSpaces()
        .capitalizeWords()

    println("원본: '$messyText'")
    println("변환 후: '$cleaned'")
}
