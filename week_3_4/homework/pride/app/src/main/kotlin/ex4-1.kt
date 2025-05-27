package app.src

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val semaphore = Semaphore(5)
    val totalTime = measureTimeMillis {
        val jobs = (1..100).map { n ->
            launch(Dispatchers.IO) {
                semaphore.withPermit {
                    println("$n 번 파일 다운로드 시작")
                    delay(500)
                    println("$n 번 파일 다운로드 완료")
                }
            }
        }
        jobs.forEach { it.join() }
    }
    println("총 걸린 시간: ${totalTime / 1000.0}s")
}