package com.kakao.map.week2

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlin.random.Random
import kotlin.time.measureTime

fun main() {
    runBlocking {
        val totalCount = 50000
        val failureList = mutableListOf<Int>()

        val totalTime = measureTime {
            val semaphore = Semaphore(100)
            (1..totalCount).map { user ->

                async(Dispatchers.IO) {
                    semaphore.withPermit {
                        delay(10)
                        val failure = Random.nextInt(100) < 10
                        when (failure) {
                            true -> {
                                failureList.add(user)
                                println("$user 번 유저 데이터 처리 실패")
                            }
                            false -> {
                                println("$user 번 유저 데이터 처리 성공")
                            }
                        } 
                    }
                }
            }.awaitAll()
        }

        println("성공한 개수 : ${totalCount - failureList.size} 개")
        println("실패한 개수 : ${failureList.size} 개")
        println("retry 목록 : $failureList")
        println("총 걸린 시간 : $totalTime")
    }
}
