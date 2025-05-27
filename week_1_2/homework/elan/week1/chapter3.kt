package week1

import kotlin.random.Random


fun nullOrNot(): String? {
    if (Random.nextInt() % 2 != 1) {
        return null
    }
    return "hello world"
}

fun main() {
//    var temp: String = null // 컴파일 오류
    var temp: String? = null
    println("Null 출력: $temp")
    println("엘비스 연산자: ${temp?: "elvis"}")
    temp = "hello"

    val nullMap = mapOf("nonnull" to "567")
    val nullValue = nullMap["null"]?.toInt()
    println("nullValue = $nullValue")
    val nonNullValue = nullMap["nonnull"]?.toInt()
    println("nonNullValue = $nonNullValue")


    // 스마트 캐스팅
    val nonNullString = nullOrNot()
    if (nonNullString != null) {
        println(nonNullString.length)
    }
}