interface ExecutableCommand {
    fun execute(): Int
}

sealed class CalcCommand: ExecutableCommand {
    data class Add(val x: Int, val y: Int): CalcCommand() { override fun execute(): Int = x + y }
    data class Subtract(val x: Int, val y: Int): CalcCommand() { override fun execute(): Int = x - y }
    data class Multiply(val x: Int, val y: Int): CalcCommand()  { override fun execute(): Int = x * y }
    data class Divide(val x: Int, val y: Int): CalcCommand() { override fun execute(): Int = x / y }
}

fun processCommand(command: CalcCommand): String {
    val result = try {
        command.execute()
    } catch (e: ArithmeticException) {
        "[Error] 0 으로 나눌 수 없습니다"
    } 

    return "$command = $result"
}

fun main() {
    println(processCommand(CalcCommand.Add(5, 3)))
    println(processCommand(CalcCommand.Subtract(10, 4)))
    println(processCommand(CalcCommand.Multiply(6, 7)))
    println(processCommand(CalcCommand.Divide(8, 2)))
    println(processCommand(CalcCommand.Divide(10, 0)))
}