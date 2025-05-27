fun processCommand(command: CalcCommand): String {
    return runCatching {
        "$command = ${command.execute()}"
    }.getOrElse {
        when(it) {
            is ArithmeticException -> "$command -> [Error] 0으로 나눌 수 없습니다."
            else -> "에러처리가 필요합니다."
        }
    }
}

fun main() {
    println(processCommand(Add(1, 2)))
    println(processCommand(Subtract(x=10, y=4)))
    println(processCommand(Multiply(x=6, y=7)))
    println(processCommand(Divide(x=8, y=2)))
    println(processCommand(Divide(x=10, y=0)))
}