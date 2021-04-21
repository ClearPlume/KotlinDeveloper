package calculator

val variableRegex = Regex("[a-zA-Z]+")
val assignmentRegex = Regex("[a-zA-Z]+ *= *(?:[a-zA-Z]+|\\d+)")
val expressionRegex = Regex("(?:(?:[-+]*\\d+)|[a-zA-Z]+)(?: +[+-]+ +(?:(?:[-+]*\\d+)|[a-zA-Z]+))*")

val variables = mutableMapOf<String, Int>()

fun main() {
    while (true) {
        val input = readLine()!!.trim()

        // When the input are...
        when {
            // commands
            input.startsWith("/") || input == "" -> {
                when (input) {
                    "" -> continue
                    "/exit" -> break
                    "/help" -> println(
                        """
                        This is a calculator. It supports addition, subtraction and saving variables
                        |Examples:
                        |> 1 -- 2 --- 3 ++++ -10 = -10
                        |> a = 2
                        |> b = a
                        |> b
                        | 2
                        |> c
                        | Unknown variable
                        | WARNING: only letters in variables names
                        |type /help to see this text
                        |type /exit to end the program
                    """.trimIndent()
                    )
                    else -> println("Unknown command")
                }
            }
            // variables
            input.matches(Regex("\\w+")) && !input.matches(Regex("\\d+")) -> {
                if (input.matches(variableRegex)) {
                    println(variables[input] ?: "Unknown variable")
                } else {
                    println("Invalid identifier")
                }
            }
            // assignment statement
            input.contains('=') -> {
                val variable = input.substringBefore('=').trim()
                val value = input.substringAfter('=').trim()

                if (variable.matches(variableRegex)) {
                    if (input.matches(assignmentRegex)) {
                        if (value.isNumeric()) {
                            variables[variable] = value.toInt()
                        } else {
                            if (value in variables) {
                                variables[variable] = variables[value]!!
                            } else {
                                println("Unknown variable")
                            }
                        }
                    } else {
                        println("Invalid assignment")
                    }
                } else {
                    println("Invalid identifier")
                }
            }
            // expression
            else -> {
                if (input.matches(expressionRegex)) {
                    val operators = input.split(Regex("\\s+"))
                    var operation = Operation.NONE

                    val result = operators.fold(0) { acc, ele ->
                        if (ele.matches(Regex("[-+]*\\d+")) || ele.matches(variableRegex)) {
                            if (operation == Operation.NONE) {
                                return@fold acc + ele.getVarValue()
                            } else {
                                val cur = when (operation) {
                                    Operation.ADDITION -> acc + ele.getVarValue()
                                    Operation.SUBTRACTION -> acc - ele.getVarValue()
                                    else -> acc
                                }

                                operation = Operation.NONE
                                return@fold cur
                            }
                        } else {
                            operation = Operation.checkOperation(ele)
                            return@fold acc
                        }
                    }

                    println(result)
                } else {
                    println("Invalid expression")
                }
            }
        }
    }
    println("Bye!")
}

private fun String.getVarValue(): Int {
    return if (this.isNumeric()) {
        this.toInt()
    } else {
        if (this in variables) {
            variables[this]!!
        } else {
            0
        }
    }
}

private fun String.isNumeric(): Boolean {
    for (c in this) {
        if (c.notDigit()) return false
    }
    return true
}

private fun Char.notDigit(): Boolean {
    if (this != '+' && this != '-' && this in '0'..'9') return false
    return true
}

private fun String.toInt(): Int {
    val splitPoint = indexOfLast { it == '+' || it == '-' }
    val modifier = if (splitPoint != -1) Operation.checkOperation(substring(0 until splitPoint)) else null
    val number = Integer.parseInt(substring(if (splitPoint == -1) 0 else splitPoint))

    return if (modifier == Operation.SUBTRACTION) number * -1 else number
}

enum class Operation {
    NONE, ADDITION, SUBTRACTION;

    companion object {
        fun checkOperation(operationSequence: String): Operation {
            return operationSequence.fold(ADDITION) { acc, operator -> if (operator == '-') !acc else acc }
        }
    }

    private operator fun not(): Operation {
        return if (this == ADDITION) SUBTRACTION else ADDITION
    }
}
