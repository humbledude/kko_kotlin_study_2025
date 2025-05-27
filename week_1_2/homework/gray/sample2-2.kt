
interface ExecutableCommand {
    fun execute(): Int
}

sealed class CalcCommand: ExecutableCommand {
    data class Add(val x:Int, val y:Int) : CalcCommand() {
        override fun execute(): Int {
            return x + y
        }
    }
    data class Subtract(val x:Int, val y:Int) : CalcCommand() {
        override fun execute(): Int {
            return x - y
        }
    }
    data class Multiply(val x:Int, val y:Int) : CalcCommand() {
        override fun execute(): Int {
            return x * y
        }
    }
    data class Divide(val x:Int, val y:Int) : CalcCommand() {
        override fun execute(): Int {
            return x / y
        }
    }
}

fun processCommand(command: CalcCommand): String {
    try {
        val result:Int = command.execute()
        var resultStr = ""
        when (command) {
            is CalcCommand.Add ->  resultStr = "Add(x=${command.x}, y=${command.y}) = $result"
            is CalcCommand.Subtract ->  resultStr = "Subtract(x=${command.x}, y=${command.y}) = $result"
            is CalcCommand.Multiply ->  resultStr = "Multiply(x=${command.x}, y=${command.y}) = $result"
            is CalcCommand.Divide -> resultStr = "Divide(x=${command.x}, y=${command.y}) = $result"
        }
        return resultStr
    } catch (e: ArithmeticException) {
        return "[Error] 0으로 나눌 수 없습니다."
    }
}


fun main() {
    val add = CalcCommand.Add(5, 3)
    val sub = CalcCommand.Subtract(10, 4)
    val mul = CalcCommand.Multiply(6, 7)
    val div = CalcCommand.Divide(8, 2)
    val div2 = CalcCommand.Divide(8, 0)
    println(processCommand(add))
    println(processCommand(sub))
    println(processCommand(mul))
    println(processCommand(div))
    println(processCommand(div2))

    return
}