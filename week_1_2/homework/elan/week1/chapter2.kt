package week1

fun 변수() {
    // 변수
    val 불변 = "처음"
//    불변 = "두번째" (오류)
    println(불변)
    var 가변 = "변경가능"
    가변 = "가변 두번째"
    println(가변)
}

fun 자료형() {
    // 자료형
    val bbb = true
    val iii = 111111
    val lll = 222222L
    val ddd = 55555.5
    val fff = 66666.6f

    println("Boolean: ${bbb}, ${bbb.javaClass}")
    println("Integer: ${iii}, ${iii.javaClass}")
    println("Long   : ${lll}, ${lll.javaClass}")
    println("Double : ${ddd}, ${ddd.javaClass}")
    println("Float  : ${fff}, ${fff.javaClass}")
}


// return 을 명시하지 않음 오류
//fun sayWorld() {
//    return "world"
//}
fun sayWorld(): String {
    return "world"
}

// 표현식 함수, 타입 추론 가능
fun sayKotlin() = "kotlin"

fun 함수() {
    println("hello: ${sayWorld()}")
    println("hello: ${sayKotlin()}")
}

fun main() {
    변수()
    자료형()
    함수()
}