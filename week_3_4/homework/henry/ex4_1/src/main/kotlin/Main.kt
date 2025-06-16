package ex4_1.src.main.kotlin

import kotlinx.coroutines.runBlocking

fun main() {
    // 4-1
//    val startTime = System.currentTimeMillis()
//    runBlocking { downloadManyWithSemaphore(100, 5) }
// //    runBlocking { downloadManyWithChunk(100, 5) }
//    val endTime = System.currentTimeMillis()
//    println("총 걸린 시간 : ${(endTime - startTime) / 1000.0}s")

    // 4-2
    val startTime = System.currentTimeMillis()
    runBlocking { fetchUserDataMany(50000, 100) }
    val endTime = System.currentTimeMillis()
    println("총 걸린 시간 : ${(endTime - startTime) / 1000.0}s")
}
