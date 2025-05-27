import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlin.random.Random
import kotlin.system.measureTimeMillis
import java.util.Collections

fun main() = runBlocking {
    // 동시 처리 제한(최대 100개)
    val semaphore = Semaphore(100)

    val successIds = Collections.synchronizedList(mutableListOf<Int>())
    val failedIds = Collections.synchronizedList(mutableListOf<Int>())

    val totalTime = measureTimeMillis {
        val jobs = (1..50000).map { id ->
            launch(Dispatchers.IO) {
                semaphore.withPermit {
                    // DB 조회
                    delay(10)
                    // 10% 확률로 실패
                    if (Random.nextInt(100) < 10) {
                        println("$id 번 유저 데이터 처리 실패")
                        failedIds.add(id)
                    } else {
                        println("$id 번 유저 데이터 처리 성공")
                        successIds.add(id)
                    }
                }
            }
        }
        jobs.joinAll()
    }

    // 결과 출력
    println("성공한 개수 : ${successIds.size} 개")
    println("실패한 개수 : ${failedIds.size} 개")
    println("retry 목록 : $failedIds")
    println("총 걸린 시간 : ${totalTime / 1000.0}s")
}