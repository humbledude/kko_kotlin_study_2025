fun main() {
    println("학생 점수를 입력하세요. (e.g., 85,90,78):")
    val input = readlnOrNull() ?: ""
    val scores = mutableListOf<Int>()

    for (s in input.split(",")) {
        s.trim().toIntOrNull()?.let {
            scores.add(it)
        }
    }

    if (scores.isNullOrEmpty()) {
        println("[Error] 점수를 올바르게 입력해주세요!")
    } else {
        println("점수 목록: $scores")

        val average = scores.average()
        println("평균 점수: $average")

        when {
            average >= 90 -> println("훌륭")
            average >= 70 -> println("그럭저럭")
            else -> println("공부하세요!")
        }
    }
}
