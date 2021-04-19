fun main(args: Array<String>) {
    if (args.size == 3) {
        args.forEachIndexed { index, arg -> println("Argument ${index + 1}: $arg. It has ${arg.length} characters") }
    } else {
        println("Invalid number of program arguments")
    }
}