package calculator

import java.util.*

fun main() {
    while (true) {
        when (val input = Scanner(System.`in`).nextLine().trim()) {
            "/exit" -> break
            "" -> continue
            "/help" -> println(
                """
                    The program calculates the sum of numbers,
                    handling addition and (binary and unary) subtraction,
                    ignoring whitespace.
                """.trimIndent()
            )
            else -> {
                val operators = input.split(Regex("\\s+"))
                var operation = Operation.NONE

                val result = operators.fold(operators[0].toInt()) { acc, ele ->
                    if (ele.matches(Regex("-?\\d+"))) {
                        if (operation == Operation.NONE) {
                            return@fold acc
                        } else {
                            val cur = when (operation) {
                                Operation.ADDITION -> acc + ele.toInt()
                                Operation.SUBTRACTION -> acc - ele.toInt()
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
            }
        }
    }
    println("Bye!")
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
