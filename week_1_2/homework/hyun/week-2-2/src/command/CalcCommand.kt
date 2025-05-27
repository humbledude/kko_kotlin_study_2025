sealed class CalcCommand(
    open val x: Int,
    open val y: Int
): ExecutableCommand {
    override fun toString(): String{
        return "(x= $x, y= $y)"
    }
}