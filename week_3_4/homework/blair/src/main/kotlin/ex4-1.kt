import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlin.system.measureTimeMillis

fun mainEx41() = runBlocking {
    val duration = measureTimeMillis {
        val fileList = (1..100).toList() // 1~100번 파일 다운로드
        val semaphore = Semaphore(5) // 한번에 5개까지만

        val jobs = fileList.map { file ->
            launch(Dispatchers.IO) { // 파일 다운로드여서 IO로 선택함
                semaphore.withPermit { downloadFile(file) }
            }
        }
        jobs.joinAll()  // 다 끝날때까지 기다려야 시간을 잴 수 있음
    }

    println("총 걸린 시간 : ${duration / 1000.0}s")
}

suspend fun downloadFile(fileNum: Int) {
    println("$fileNum 번 파일 다운로드 시작")
    delay(500L) // 예시: API 호출 대기
    println("$fileNum 번 파일 다운로드 완료")
}