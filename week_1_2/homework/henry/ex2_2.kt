fun main() {
    println(processCommand(Add(5, 3)))
    println(processCommand(Subtract(10, 4)))
    println(processCommand(Multiply(6, 7)))
    println(processCommand(Divide(8, 2)))
    println(processCommand(Divide(10, 0)))
}

interface ExecutableCommand {
    fun execute(): Int
}

sealed class CalcCommand : ExecutableCommand {
    abstract val x: Int
    abstract val y: Int

    override fun toString(): String = "${this::class.simpleName}(x=$x, y=$y)"
}

data class Add(
    override val x: Int,
    override val y: Int,
) : CalcCommand() {
    override fun execute(): Int = x + y
}

data class Subtract(
    override val x: Int,
    override val y: Int,
) : CalcCommand() {
    override fun execute(): Int = x - y
}

data class Multiply(
    override val x: Int,
    override val y: Int,
) : CalcCommand() {
    override fun execute(): Int = x * y
}

data class Divide(
    override val x: Int,
    override val y: Int,
) : CalcCommand() {
    override fun execute(): Int = if (y != 0) x / y else throw IllegalArgumentException("0으로 나눌 수 없습니다.")
}

fun processCommand(command: CalcCommand): String =
    try {
        "$command = ${command.execute()}"
    } catch (e: Exception) {
        "$command -> [Error] ${e.message}"
    }
