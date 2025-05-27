package week2

fun String.reverseText(): String {
    return this.reversed()
}

fun operate(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
    return operation(a, b)
}

fun main() {
    // 확장 함수
    val text = "Hello!"
    println(text.reverseText())
    println("Kotlin".reverseText())

    // 고차 함수
    println(operate(3, 4, { x, y -> x + y}))
}