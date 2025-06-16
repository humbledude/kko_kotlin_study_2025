#!/usr/bin/env kscript

@file:DependsOn("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
@file:DependsOn("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.6.4")

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlin.system.measureTimeMillis

suspend fun downloadFile(i: Int) {
    println("${i} 번 파일 다운로드 시작")
    delay(500)
    println("${i} 번 파일 다운로드 완료")
}

runBlocking {
    val semaphore = Semaphore(5)

    val totalTime = measureTimeMillis {
        coroutineScope {
            (1..100).map { i ->
                launch(Dispatchers.IO) {
                    semaphore.withPermit {
                        downloadFile(i)
                    }
                }
            }
        }
    }

    println("총 걸린 시간 : ${totalTime / 1000.0}s")
}
