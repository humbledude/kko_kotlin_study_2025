import java.util.*

private const val WHITE_SPACE = " "
private const val DOUBLE_WHITE_SPACE = WHITE_SPACE + WHITE_SPACE
private const val UNDER_BAR = "_"

/**
 * 규칙 함수들
 */

// 연속된 공백을 하나로 줄이기
fun removeExtraSpaces(str: String): String {
    return replaceWhiteSpaceWithRecursive(str)
}

private fun replaceWhiteSpaceWithRecursive(str: String): String {
    val replaceStr = str.replace(DOUBLE_WHITE_SPACE, WHITE_SPACE)
    if (replaceStr.contains(DOUBLE_WHITE_SPACE)) return replaceWhiteSpaceWithRecursive(replaceStr)
    return replaceStr
}

// 앞 뒤 공백 제거
fun trimAll(str: String): String {
    return str.trim()
}

// 띄어쓰기를 _ 로 바꾸고 모두 소문자 변환
fun toSnakeCase(str: String): String {
    return str.replace(WHITE_SPACE, UNDER_BAR).lowercase(Locale.getDefault())
}

// 각 단어의 첫글자를 대문자로
fun capitalizeWords(str: String): String {
    return str.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

/**
 * 확장 함수
 */

fun String.cleanUp(executeFun: (String) -> String): String {
    return executeFun(this)
}
