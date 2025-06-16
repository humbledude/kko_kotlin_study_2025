import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun mainEx42() = runBlocking {
    val duration = measureTimeMillis {
        val userIdList = (1..50000).toList() // 1~50,000건 데이터 리스트
        val semaphore = Semaphore(100) // 한번에 100개까지만

        val passUserIdList = mutableListOf<Int>() // 성공 유저
        val retryUserIdList = mutableListOf<Int>() // 실패 유저

        val jobs = userIdList.map { userId ->
            launch(Dispatchers.IO) { // DB 접근이어서 IO로 선택함
                semaphore.withPermit {
                    val isSucess: Boolean = processUserData(userId)
                    when {
                        isSucess -> passUserIdList.add(userId)
                        else -> retryUserIdList.add(userId)
                    }
                }
            }
        }
        jobs.joinAll() // 다 끝날때까지 기다려야 시간을 잴 수 있음

        println("성공한 개수: ${passUserIdList.count()}")
        println("실패한 개수: ${retryUserIdList.count()}")
        println("retry 목록:  ${retryUserIdList}")
    }

    println("총 걸린 시간 : ${duration / 1000.0}s")
}

suspend fun processUserData(userId: Int): Boolean {
    delay(100L) // 예시: API 호출 대기

    val isSuccess = Random.nextInt(100) >= 10 // 헷갈려서 성공할 확률로 바꿈
    println("$userId 번 유저 처리 " + if (isSuccess) "성공" else "실패")

    return isSuccess
}