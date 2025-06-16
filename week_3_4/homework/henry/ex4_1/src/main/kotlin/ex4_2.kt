package ex4_1.src.main.kotlin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlin.random.Random

class FetchUserException(
    val userId: Int,
    override val message: String?,
) : RuntimeException(message)

suspend fun fetchUserData(userId: Int): Int {
    delay(10)
    if (Random.nextInt(100) < 10) {
        println("$userId 번 유저 데이터 처리 실패")
        throw FetchUserException(userId, "유저 처리 실패했어용.. $userId")
    }
    println("$userId 번 유저 데이터 처리 성공")
    return userId
}

suspend fun fetchUserDataMany(
    number: Int,
    maxParallelSize: Int,
) = supervisorScope {
    val semaphore = Semaphore(maxParallelSize)
    val successIds = mutableListOf<Int>()
    val failedIds = mutableListOf<Int>()

    runBlocking {
        val result =
            (1..number)
                .map { i ->
                    async(Dispatchers.IO) {
                        semaphore.withPermit {
                            try {
                                fetchUserData(i)
                            } catch (e: FetchUserException) {
                                failedIds.add(e.userId)
                                null
                            }
                        }
                    }
                }.awaitAll()
                .filterNotNull()
        successIds.addAll(result)
    }

    println("성공한 개수: ${successIds.size} 개")
    println("실패한 개수: ${failedIds.size} 개")
    println("retry 목록: $failedIds")
}
