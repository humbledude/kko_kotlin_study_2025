import java.util.Random

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlin.system.*


val random = Random()

class Task(val number: Int) {
    suspend fun process() : Boolean{
        delay(10)
        val isSuccessful = (random.nextInt(100) >= 10)
        println("${number} 번 유저 데이터 처리 " + if (isSuccessful) "성공" else "실패")
        return isSuccessful
    }
}




fun mainEx42() = runBlocking {

    val users = 50000
    val semaphore = Semaphore(100)
    val successfulList = mutableListOf<Int>()
    val failedList = mutableListOf<Int>()


    val time = measureTimeMillis {
        (1 .. users).map {
            launch(Dispatchers.IO) {
                semaphore.withPermit {
                    val isSuccessful = Task(it).process()
                    if (isSuccessful) {
                        successfulList.add(it)
                    } else {
                        failedList.add(it)
                    }
                }
            }
        }.forEach{ it.join() }

        println("성공한 개수: ${successfulList.count()}")
        println("실패한 개수: ${failedList.count()}")
        println("retry 목록:  ${failedList}")
    }

    println("총 걸린 시간 : ${time / 1000.0}s")
}