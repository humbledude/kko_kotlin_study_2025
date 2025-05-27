fun main() {
    // 1. 사용자 입력을 받는다. (readlnOrNull() 사용), 콤마로 여러 점수를 입력 받는다.
    println("학생 점수를 입력하세요. (e.g., 85,90,78):")
    val input = readlnOrNull()

    // 2. 점수 데이터를 콤마로 나누고 공백을 제거해준다.
    // ㄴ run을 쓴 이유: let은 it(수신객체)를 명시해야됨. run과 apply는 명시하지 않아도 됨.
    // ㄴ apply는 수신객체를 반환하고 run은 마지막 줄을 반환하기 때문에 run 사용.
    val scoreList = input?.run {
        split(',')
            .map { it.replace(" ", "")}
            .filter { it.isNotEmpty() }
    } ?: emptyList()

    // 3. 전부 빈 값을 입력했을 때 (e.g. , , ,) 오류 메시지 추력
    if(scoreList.isEmpty()) {
        println("사용자의 입력이 빈값입니다. 정확한 입력값을 입력하세요.")
        return
    }

    // 4. 평균 계산을 해주고 출력해준다.
    val avg = scoreList.sumOf { it.toDouble() }.div(scoreList.size)
    avg.run {
        println("점수 목록 : $scoreList") // 클로저
        println("평균 점수 : $this")
    }

    // 5. when 을 이용해 결과에 대해 출력을 해준다.
    when {
        avg >= 90 -> {
            println("훌륭")
        }
        avg >= 70 -> {
            println("그럭저럭")
        }
        else -> {
            println("공부하세요!")
        }
    }

}