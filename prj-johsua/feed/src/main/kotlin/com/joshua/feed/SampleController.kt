package com.joshua.feed

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/sample")
class SampleController {

    @GetMapping("/hello")
    suspend fun hello(): String {
        return "Hello from WebFlux with Coroutines!"
    }

    @GetMapping("/greeting/{name}")
    suspend fun greeting(@PathVariable name: String): Map<String, Any> {
        return mapOf(
            "message" to "Hello, $name!",
            "timestamp" to LocalDateTime.now(),
            "status" to "success"
        )
    }

    @GetMapping("/items")
    suspend fun getItems(): Flow<SampleItem> {
        return flowOf(
            SampleItem(1, "첫 번째 아이템", "설명 1"),
            SampleItem(2, "두 번째 아이템", "설명 2"),
            SampleItem(3, "세 번째 아이템", "설명 3")
        )
    }

    @PostMapping("/items")
    suspend fun createItem(@RequestBody item: SampleItem): SampleItem {
        // 비동기 작업 시뮬레이션
        delay(100)
        return item.copy(id = System.currentTimeMillis())
    }

    @GetMapping("/delay")
    suspend fun delayedResponse(): String {
        delay(2000) // 2초 지연
        return "지연된 응답입니다! (coroutine 스타일)"
    }

    @GetMapping("/streaming")
    suspend fun streamingItems(): Flow<SampleItem> {
        return kotlinx.coroutines.flow.flow {
            repeat(5) { index ->
                delay(1000) // 1초마다 아이템 방출
                emit(SampleItem(
                    id = index.toLong(),
                    name = "스트리밍 아이템 ${index + 1}",
                    description = "실시간으로 생성된 아이템"
                ))
            }
        }
    }

    @GetMapping("/async-process")
    suspend fun asyncProcess(): Map<String, Any> = coroutineScope {
        // 여러 비동기 작업을 동시에 실행
        val start = System.currentTimeMillis()
        
        val task1 = async {
            delay(1000)
            "Task 1 완료"
        }
        
        val task2 = async {
            delay(1500)
            "Task 2 완료"
        }
        
        val results = listOf(task1.await(), task2.await())
        val end = System.currentTimeMillis()
        
        mapOf(
            "results" to results,
            "duration" to "${end - start}ms",
            "message" to "병렬 처리 완료"
        )
    }
}

data class SampleItem(
    val id: Long,
    val name: String,
    val description: String
)
