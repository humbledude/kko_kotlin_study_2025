# 3,4 주차 학습 가이드

## 학습 목표

✅ 3주차: 컬렉션, 람다, 스트림 처리
	•	List, Set, Map 등 컬렉션 다루기
	•	람다 표현식과 Stream 처리
	•	Scope Functions (let, run, apply, also, with)
	•	실습: 코틀린의 컬렉션 API를 사용한 데이터 처리

✅ 4주차: 비동기 처리 & 코루틴
	•	코루틴 기본 개념 (suspend, async/await, launch)
	•	Flow와 Channel 활용
	•	실습: 간단한 비동기 API 호출 및 데이터 처리



## 공식 문서 링크 모음

# ✅ 3주차: 컬렉션, 람다, 스트림 처리

## 📌 컬렉션 다루기

- **컬렉션 개요 및 종류 (`List`, `Set`, `Map`)**  
    [Collections Overview](https://kotlinlang.org/docs/collections-overview.html)
    
- **컬렉션 생성 및 변형 함수 (`listOf`, `mapOf`, `mutableListOf` 등)**  
    [Constructing Collections](https://kotlinlang.org/docs/constructing-collections.html)
    
- **컬렉션 연산 (`filter`, `map`, `reduce`, `groupBy` 등)**  
    [Collection Operations Overview](https://kotlinlang.org/docs/collection-operations.html)
    
- **컬렉션 필터링**  
    [Filtering Collections](https://kotlinlang.org/docs/collection-filtering.html)
    
- **컬렉션 변환 (`map`, `mapIndexed`, `mapNotNull` 등)**  
    [Collection Transformation Operations](https://kotlinlang.org/docs/collection-transformations.html)
    
- **컬렉션 집계 연산 (`sum`, `average`, `count`, `minOrNull`, `maxOrNull` 등)**  
    [Aggregate Operations](https://kotlinlang.org/docs/collection-aggregate.html)
    

## 📌 람다 표현식과 고차 함수

- **고차 함수와 람다 표현식 개요**  
    [Higher-Order Functions and Lambdas](https://kotlinlang.org/docs/lambdas.html)
    
- **SAM 변환 (단일 추상 메서드 인터페이스)**  
    [Functional (SAM) Interfaces](https://kotlinlang.org/docs/fun-interfaces.html)
    
- **인라인 함수와 `inline`, `noinline`, `crossinline` 키워드**  
    [Inline Functions](https://kotlinlang.org/docs/inline-functions.html)
    

## 📌 Scope Functions (`let`, `run`, `apply`, `also`, `with`)

- **Scope Functions 개요 및 사용법**  
    [Scope Functions](https://kotlinlang.org/docs/scope-functions.html)
    

---

# ✅ 4주차: 비동기 처리 & 코루틴

## 📌 코루틴 기본 개념 (`suspend`, `async/await`, `launch`)

- **코루틴 개요 및 기본 사용법**  
    [Coroutines Basics](https://kotlinlang.org/docs/coroutines-basics.html)
    
- **코루틴 가이드 (코루틴의 주요 기능 및 예제)**  
    [Coroutines Guide](https://kotlinlang.org/docs/coroutines-guide.html)
    

## 📌 Dispatcher 종류

- **코루틴 컨텍스트와 디스패처 (`Dispatchers.Default`, `Dispatchers.IO`, `Dispatchers.Main` 등)**  
    [Coroutine Context and Dispatchers](https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html)
    

## 📌 동시성 제어

- **코루틴 예외 처리 및 취소**  
    [Coroutine Exceptions Handling](https://kotlinlang.org/docs/exception-handling.html)
    
- **코루틴 취소와 타임아웃**  
    [Cancellation and Timeouts](https://kotlinlang.org/docs/cancellation-and-timeouts.html)
	
- **데이터 분할 처리 (`chunked`)**
    
    - [Iterable.chunked](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/chunked.html)
        
    - [CharSequence.chunked](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.text/chunked.html)
        
- **코루틴 동시 실행 제한 (`Semaphore`)**
    
    - [Semaphore 클래스](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.sync/-semaphore/)
        
    - [acquire 함수](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.sync/-semaphore/acquire.html)
        
    - [release 함수](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.sync/-semaphore/release.html)
        
    - [withPermit 함수](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.sync/with-permit.html)

## 📌 Flow와 Channel 활용

- **Flow 개요 및 사용법**  
    [Asynchronous Flow](https://kotlinlang.org/docs/flow.html)
    
- **Channel 개요 및 사용법**  
    [Channels](https://kotlinlang.org/docs/channels.html)