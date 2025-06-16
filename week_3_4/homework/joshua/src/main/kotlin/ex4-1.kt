
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlin.system.*

class File(val number: Int) {
    suspend fun download() {
        println("${number} 번 파일 다운로드 시작")
        delay(500)
        println("${number} 번 파일 다운로드 완료")
    }

    suspend fun downloadWithSemaphore(semaphore: Semaphore) {
        semaphore.withPermit {
            download()
        }
    }
}

val allFiles = (1..100)
    .map {File(it)}
    .toList()



fun mainEx41() = runBlocking {
    println("==== semaphore 사용 ====")
    val time = measureTimeMillis {
        val semaphore = Semaphore(permits = 5)
        allFiles.map { 
            launch(Dispatchers.IO) {
                it.downloadWithSemaphore(semaphore)
            }
        }.forEach{ it.join() }
    }

    println("총 걸린 시간 : ${time / 1000.0}s")


    println("==== chunked 사용 ====")
    val time2 = measureTimeMillis {
        allFiles.chunked(5).forEach { chunk -> 
            chunk.map {
                launch(Dispatchers.IO) {
                    it.download()
                }
            }.forEach { it.join() }
        }
    }

    println("총 걸린 시간 : ${time2 / 1000.0}s")

}