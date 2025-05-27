package week1

fun main() {
    println("학생 점수를 입력하세요. (e.g., 85,90,78):")

    val scoreList : List<Int> = readln()
        .split(",")
        .mapNotNull{it.trim().toIntOrNull()}

    if(scoreList.isEmpty()) {
        print("전부 빈 값이에요")
        return;
    }
    println("점수 목록: $scoreList")

    val average : Double = scoreList.average();
    println("평균 점수: $average")

    val message = when {
        average >= 90 -> "훌륭"
        average >= 70 -> "그럭저럭"
        else -> "공부하세요!"
    }
    println(message)
}