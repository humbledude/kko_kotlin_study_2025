fun main() {
    println("학생 점수를 입력하세요. (e.g. 85,90,78)")
    print("> ")
    val input: String? = readlnOrNull()

    if (input == null || input.length == 0) {
        println("제대로 입력합시다 (입력없음)")
        return
    }

    val scores: List<Int?> = input.split(",").map() {it -> it.trim()} .map(String::toIntOrNull).toList()

    if (scores.filterNotNull().size != scores.size) {
        println("제대로 입력합시다 (숫자 외)")
        return
    }

    println("점수 목록: $scores")
    val avg = scores.filterNotNull().average()
    println("평균 점수: $avg")

    val msg = when {
       avg >= 90 -> "훌륭"
       avg >= 70 -> "그럭저럭"
       else -> "공부하세요!"
    }
    println(msg)
}