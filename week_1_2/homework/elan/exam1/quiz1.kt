package exam1

fun result(avg: Double) = when {
    avg >= 90 -> "훌륭"
    avg >= 80 -> "그럭저럭"
    else -> "공부하세요!"
}


fun main() {
    println("학생 점수를 입력하세요. (e.g., 85,90,78):")
    val inputLine = readlnOrNull()
    val stringValues = inputLine?.split(',')
        ?.map { it.trim() }
    stringValues?.let {
        println(it)
        val avg = it.map { v -> v.toInt() }.average()
        println("평균 점수: $avg")
        println(result(avg))
    }
}