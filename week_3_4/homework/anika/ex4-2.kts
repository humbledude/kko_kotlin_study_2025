#!/usr/bin/env kscript

@file:DependsOn("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
@file:DependsOn("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.6.4")

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import java.util.Collections
import kotlin.random.Random
import kotlin.system.measureTimeMillis

suspend fun fetchUserData(): Boolean {
    delay(10)
    return Random.nextInt(100) >= 10
}

runBlocking {
    val successIds = Collections.synchronizedSet(mutableSetOf<Int>())
    val failIds = Collections.synchronizedSet(mutableSetOf<Int>())

    val semaphore = Semaphore(100)

    val totalTime = measureTimeMillis {
        coroutineScope {
            (1..50_000).forEach { i ->
                launch(Dispatchers.IO) {
                    semaphore.withPermit {
                        val success = fetchUserData()
                        if (success) {
                            println("${i} 번 유저 데이터 성공")
                            successIds.add(i)
                        } else {
                            println("${i} 번 유저 데이터 처리 실패")
                            failIds.add(i)
                        }
                    }
                }
            }
        }
    }

//    // 실패한 것들 3번까지 retry
//    val retryFailIds = Collections.synchronizedSet(mutableSetOf<Int>())
//    retryFailIds.addAll(failIds)
//
//    repeat(3) { retry ->
//        println("### Retry ${retry + 1}차 시작")
//
//        coroutineScope {
//            retryFailIds.toList().forEach { i ->
//                launch(Dispatchers.IO) {
//                    semaphore.withPermit {
//                        val success = fetchUserData()
//                        if (success) {
//                            println("${i} 번 유저 데이터 재시도 성공")
//                            retryFailIds.remove(i)
//                        } else {
//                            println("${i} 번 유저 데이터 재시도 실패")
//                        }
//                    }
//                }
//            }
//        }
//
//        if (retryFailIds.isEmpty()) return@repeat
//    }
//    println("retry 성공한 개수 : ${failIds.size - retryFailIds.size}")
//    println("retry 실패한 개수 : ${retryFailIds.size}")

    println("성공한 개수 : ${successIds.size} 개")
    println("실패한 개수 : ${failIds.size} 개")
    println("retry 목록 : $failIds")
    println("총 걸린 시간 : ${totalTime / 1000.0}s")
}
