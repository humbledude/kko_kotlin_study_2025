package ex4_1.src.main.kotlin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

/**
 * - 다운로드 기능
 * - 한 번에 5개의 파일씩 제한하는 기능
 */

suspend fun downloadFile(index: Int) {
    println("$index 번 파일 다운로드 시작")
    delay(500)
    println("$index 번 파일 다운로드 완료")
}

suspend fun downloadManyWithSemaphore(
    number: Int,
    maxParallelSize: Int,
) = coroutineScope {
    val semaphore = Semaphore(maxParallelSize)
    (1..number)
        .map { i ->
            launch(Dispatchers.IO) {
                semaphore.withPermit {
                    downloadFile(i)
                }
            }
        }.joinAll() // 작업 종료 대기
}

suspend fun downloadManyWithChunk(
    number: Int,
    chunkSize: Int,
) = coroutineScope {
    (1..number)
        .chunked(chunkSize)
        .forEach { chunked ->
            val jobs =
                chunked
                    .map { i ->
                        launch(Dispatchers.IO) {
                            downloadFile(i)
                        }
                    }
            jobs.joinAll() // 작업 종료 대기
        }
}
