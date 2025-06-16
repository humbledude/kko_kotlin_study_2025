package com.kakao.map.week2

import kotlinx.coroutines.*
import kotlin.time.measureTime

fun main() {
    runBlocking {
        val totalTime = measureTime {
            (1..100).chunked(5).forEach { files ->
                val jobs = files.map { file ->
                    async(Dispatchers.IO) {
                        println("$file 번 파일 다운로드 시작")
                        delay(500)
                        println("$file 번 파일 다운로드 완료")
                    }
                }
                jobs.awaitAll()
            }
        }
        println("총 걸린 시간 : $totalTime")
    }
}
