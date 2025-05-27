package week2

data class User(
    val id: Int,
    val name: String,
)

object DataConnection {
    fun connect(): String = "데이터베이스에 연결되었습니다."
}

// 자바처럼 싱글톤
class StringUtil private constructor() {
    companion object {
        fun reverseText(text: String): String {
            return text.reversed()
        }
    }
}

fun main() {
    // 데이터 클래스
    val user1 = User(1, "앨리스")
    val user2 = user1.copy(name = "밥")
    println(user1)
    println(user2)


    // 싱글톤
    println(DataConnection.connect())
    println(StringUtil.reverseText("World!"))
}