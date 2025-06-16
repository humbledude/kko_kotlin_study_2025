package hyunk.week_4_1

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.time.measureTime

@SpringBootApplication
class Week41Application

fun main(args: Array<String>) {
    runApplication<Week41Application>(*args)

    val startTime = System.currentTimeMillis()
    runBlocking {
        // 한번에 5개의 파일을 다운로드 할 수 있다고 가정한다. (chunked 나 semaphore 사용)
        val semaphore = Semaphore(5)

        // 1~100 번의 번호를 가진 파일을 다운로드 한다고 가정한다.
        // 모든 파일이 전부 다운로드 완료 후에 총 걸린 시간을 출력한다.
        for (fileNum in 1..100) {
            launch(Dispatchers.IO) {        // 요청은 네트워크 요청이기 때문에 Dispatcher 종류를 적절하게 이용한다.
                semaphore.withPermit {      // 세마포어
                    downloadFile(fileNum)
                }
            }
        }
    }
    val endTime = System.currentTimeMillis()
    println("총 걸린 시간 : ${(endTime - startTime).toDouble() / 1000}s")
}

suspend fun downloadFile(fileNum: Int) {
    // 각 다운로드 작업은 "n번 파일 다운로드 시작" -> "n번 파일 다운로드 완료" 를 출력한다.
    println("${fileNum}번 파일 다운로드 시작")
    delay(500) // 다운로드는 500 ms 가 걸린다고 가정 한다 (deply(500) 을 넣어준다.)
    println("${fileNum}번 파일 다운로드 완료")
}
