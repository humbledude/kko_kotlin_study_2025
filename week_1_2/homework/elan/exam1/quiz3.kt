package exam1

import exam1.CalcCommand.*

interface ExecutableCommand {
    fun execute(): Int
}

sealed class CalcCommand: ExecutableCommand {
    data class Add(val x: Int, val y: Int) : CalcCommand() {
        override fun execute() = x + y
    }
    data class Subtract(val x: Int, val y: Int) : CalcCommand() {
        override fun execute() = x - y
    }
    data class Multiply(val x: Int, val y: Int) : CalcCommand() {
        override fun execute() = x * y
    }
    data class Divide(val x: Int, val y: Int) : CalcCommand() {
        override fun execute() = x / y
    }
}
fun handleResult(calcCommand: CalcCommand): String =
    when(calcCommand) {
        is Divide -> try {
            "$calcCommand = ${calcCommand.execute()}"
        } catch (_: Exception) {
            "$calcCommand -> [Error] 0으로 나눌 수 없습니다."
        }
        else -> "$calcCommand = ${calcCommand.execute()}"
}

fun main() {
    println(handleResult(Add(5, 3)))
    println(handleResult(Subtract(10, 4)))
    println(handleResult(Multiply(6, 7)))
    println(handleResult(Divide(8, 2)))
    println(handleResult(Divide(10, 0)))
}