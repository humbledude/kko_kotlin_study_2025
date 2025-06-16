
fun main(args: Array<String>) {
    if (args.size == 0) {
        return
    }

    when (args[0]) {
        "ex3" -> mainEx3()
        "ex41" -> mainEx41()
        "ex42" -> mainEx42()
    }
}