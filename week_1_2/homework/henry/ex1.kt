

fun main() {
    ScoreCalculator.run()
}

object ScoreCalculator {
    private const val SPLIT_DELIMITER = ","

    fun run() {
        val userInput = readInput()
        val scores = parseString(userInput)
        val result = average(scores)
        evaluateAverageScore(result)
    }

    private fun average(
        scores: List<Int>,
        showInput: Boolean = true,
    ): Double {
        val averageScore = if (scores.isNotEmpty()) scores.average() else 0.0
        if (showInput) {
            println("평균 점수: $averageScore")
        }

        return averageScore
    }

    private fun evaluateAverageScore(score: Double) {
        val evaluateString =
            when {
                score >= 90 -> "훌륭"
                score >= 70 -> "그럭저럭"
                else -> "공부하세요!"
            }
        println(evaluateString)
    }

    private fun parseString(
        input: String,
        showInput: Boolean = true,
    ): List<Int> {
        try {
            val scores =
                input
                    .split(SPLIT_DELIMITER)
                    .map {
                        it.trim().toInt()
                    }
            if (showInput) {
                println("점수 목록: $scores")
            }

            return scores
        } catch (e: NumberFormatException) {
            println("변환 실패 ㅠㅠ")
            throw e
        }
    }

    private fun readInput(): String {
        println("학생 점수를 입력하세요. (e.g. 85,90,78):")
        return readlnOrNull() ?: throw IllegalArgumentException("뭘 입력하신거죠?")
    }
}
