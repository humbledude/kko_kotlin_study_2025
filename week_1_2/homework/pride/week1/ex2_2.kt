package week1

interface ExecutableCommand {
    fun execute(): Int
}

sealed class CalcCommand : ExecutableCommand

data class Add(val x: Int, val y: Int) : CalcCommand(){
    override fun execute(): Int = x+y;
}

data class Subtract(val x: Int, val y: Int) : CalcCommand(){
    override fun execute(): Int = x - y;
}

data class Multiply(val x: Int, val y: Int) : CalcCommand(){
    override fun execute(): Int = x * y;
}

data class Divide(val x: Int, val y: Int) : CalcCommand(){
    override fun execute(): Int {
        if (y == 0) throw IllegalArgumentException("[Error] 0으로 나눌 수 없습니다.")
        return x / y
    }
}

fun processCommand(command: ExecutableCommand) {
    when (command) {
        is Add -> println("$command = ${command.execute()}")
        is Subtract -> println("$command = ${command.execute()}")
        is Multiply -> println("$command = ${command.execute()}")
        is Divide -> {
            try{
                println("$command = ${command.execute()}")
            } catch (e: Exception){
                println("$command → ${e.message}")
            }
        }
    }
}

fun main(){
    processCommand(Add(5,3))
    processCommand(Subtract(10,4))
    processCommand(Multiply(6,7))
    processCommand(Divide(8,2))
    processCommand(Divide(12,0))
}