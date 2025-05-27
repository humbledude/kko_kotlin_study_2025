interface ExecutableCommand {
    fun execute(): Int
}

sealed class CalcCommand : ExecutableCommand

data class Add(val x: Int, val y: Int) : CalcCommand() {
    override fun execute(): Int = x + y
}

data class Subtract(val x: Int, val y: Int) : CalcCommand() {
    override fun execute(): Int = x - y
}

data class Multiply(val x: Int, val y: Int) : CalcCommand() {
    override fun execute(): Int = x * y
}

data class Divide(val x: Int, val y: Int) : CalcCommand() {
    override fun execute(): Int = if (y == 0) throw IllegalArgumentException("[Error] 0으로 나눌 수 없습니다.") else x / y
}

fun processCommand(command: CalcCommand): String {
    return when (command) {
        is Add -> "Add(x=${command.x}, y=${command.y}) = ${command.execute()}"
        is Subtract -> "Subtract(x=${command.x}, y=${command.y}) = ${command.execute()}"
        is Multiply -> "Multiply(x=${command.x}, y=${command.y}) = ${command.execute()}"
        is Divide -> try {
            "Divide(x=${command.x}, y=${command.y}) = ${command.execute()}"
        } catch (e: IllegalArgumentException) {
            "Divide(x=${command.x}, y=${command.y}) -> ${e.message}"
        }
    }
}

fun main() {
    println(processCommand(Add(5, 3)))
    println(processCommand(Subtract(10, 4)))
    println(processCommand(Multiply(6, 7)))
    println(processCommand(Divide(8, 2)))
    println(processCommand(Divide(10, 0)))
}