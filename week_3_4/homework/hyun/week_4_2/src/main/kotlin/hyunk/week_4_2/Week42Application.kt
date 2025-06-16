package hyunk.week_4_2

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.random.Random

@SpringBootApplication
class Week42Application

// 이 부분 공부가 부족하여.. 구글링 했습니다ㅠㅠ 그래도 지피티 선생님 도움은 안받았습니다..
// https://tech.kakaopay.com/post/coroutine-exceptions-handling/
fun main(args: Array<String>) {
    runApplication<Week42Application>(*args)

    val retryList = mutableListOf<String>();

    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val userId = throwable.message
        println("$userId 번 유저 데이터 처리 실패" )
        userId?.let { retryList.add(userId) }
    }

    val startTime = System.currentTimeMillis()
    runBlocking {
        supervisorScope {
            val semaphore = Semaphore(100)

            for (userId in 1..50000) {
                launch(coroutineExceptionHandler) {
                    semaphore.withPermit {
                        getUserData(userId)
                    }
                }
            }
        }
    }
    val endTime = System.currentTimeMillis()

    println("성공한 개수 : ${50000 - retryList.size} 개")
    println("실패한 개수 : ${retryList.size} 개")
    println("retry 목록 : $retryList")
    println("총 걸린 시간 : ${(endTime - startTime).toDouble() / 1000}s")
}

suspend fun getUserData(userId: Int) {
    delay(10)
    if (Random.nextInt(100) < 10) {
        throw CoroutineException(message = userId.toString())
    }
    println("$userId 번 유저 데이터 처리 성공" )
}

class CoroutineException(message:String): RuntimeException(message)
