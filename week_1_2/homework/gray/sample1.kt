
fun calcMean(numList:List<Int>):Double {
    var sum = 0
    for (num in numList) {
        sum += num
    }
    return sum.toDouble() / numList.size
}


fun main() {
	print("학생 점수를 입력하세요. (e.g., 85,90,78):")
	val input:String = readlnOrNull() ?: ""
	val numList : List<Int> = input.split(',').mapNotNull { it.toIntOrNull() }
    print("점수 목록: $numList")
	val result = calcMean(numList)
    println("평균 점수: $result")
    when {
        result >= 90 -> println("훌륭")
        result >= 70 -> println("그럭저럭")
        else -> println("공부하세요!")
    }

    return
}

