package calculator

import java.math.BigInteger
import java.util.*

private val variableRegex = Regex("[a-zA-Z]+")
private val assignmentRegex = Regex("[a-zA-Z]+ *= *(?:[a-zA-Z]+|[+-]*\\d+)")
private val expressionRegex =
    Regex("\\(*(?:(?:[-+]*\\d+)|[a-zA-Z]+)\\)*(?: *\\(*(?:[+-]+|[*/^])\\)* *\\(*(?:(?:[-+]*\\d+)|[a-zA-Z]+)\\)*)*")
val operatorRegex = Regex("[+-]+|[*/^()]")

private val variables = mutableMapOf<String, BigInteger>()

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
                        This is a calculator. It supports addition, subtraction, multiplication, division and saving variables
                        |Examples:
                        |> 1 -- 2 --- 3 ++++ -10 * 2 + 1 = -19
                        |> a = 2
                        |> b = a
                        |> b
                        | 2
                        |> c
                        | Unknown variable
                        |> a *** b
                        | Invalid expression
                        |> a // b)
                        | Invalid expression
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
                if (input.matches(expressionRegex, true)) {
                    // Parse [expression] into List<[ExpressionUnit]>
                    val infixExpression = parseExpression(StringBuilder(input.replace(Regex("\\s+"), "")))
                    // From infix to postfix
                    val postfixExpression = infixToPost(infixExpression)
                    println(postfixCalculate(postfixExpression))
                } else {
                    println("Invalid expression")
                }
            }
        }
    }
    println("Bye!")
}

/**
 * For assignment statement, check whether the user's input is a number or a variable
 */
private fun String.isNumeric(): Boolean {
    for (c in this) {
        if (!c.isNum()) return false
    }
    return true
}

/**
 * For assignment statement's check, because there may be these "numbers": -10 10 +10 --10 ++10 -+-10 etc
 */
private fun Char.isNum(): Boolean {
    if (this in arrayOf('+', '-') || this in '0'..'9') return true
    return false
}

/**
 * For assignment statement, cast from numeric string to a number, because there may be these "numbers": -10 10 +10 --10 ++10 -+-10 etc
 */
private fun String.toInt(): BigInteger {
    val splitPoint = indexOfLast { it == '+' || it == '-' }
    val modifier = if (splitPoint != -1) Operator.checkPlusMinus(substring(0 until splitPoint)) else null
    val number = BigInteger(substring(if (splitPoint == -1) 0 else splitPoint))

    return if (modifier == Operator.SUBTRACTION) number * BigInteger("-1") else number
}

/**
 * For expressions, check whether the user's input has paired brackets
 */
private fun String.matches(regex: Regex, checkBrackets: Boolean): Boolean {
    val brackets = Stack<Char>()

    if (checkBrackets) {
        for (char in this) {
            if (char == '(') brackets.push(char)

            if (char == ')') {
                if (brackets.empty()) {
                    brackets.push(char)
                    break
                } else {
                    brackets.pop()
                }
            }
        }
    }

    return regex.matches(this) && brackets.empty()
}

/**
 * Parse [expression] into List<[ExpressionUnit]>
 */
private fun parseExpression(expression: StringBuilder): List<ExpressionUnit> {
    val expressions = mutableListOf<ExpressionUnit>()

    run {
        var unit: ExpressionUnit?

        do {
            unit = expression.nextUnit()

            if (unit != null) {
                expressions.add(unit)
            }
        } while (unit != null)
    }

    lastUnitIndex = 0
    return expressions
}

private var lastUnitIndex: Int = 0

private fun StringBuilder.nextUnit(): ExpressionUnit? {
    var next = StringBuilder()
    var lastChar: Char
    var lastNumber = false

    if (lastUnitIndex < lastIndex) {
        lastChar = get(lastUnitIndex)
        next = next.append(lastChar)

        for (i in lastUnitIndex + 1..lastIndex) {
            val c = get(i)

            if (lastChar same c) {
                lastChar = c
                next = next.append(lastChar)
                lastUnitIndex = i
            } else {
                lastUnitIndex = i
                break
            }

            lastNumber = i == lastIndex
        }
    } else if (lastUnitIndex == lastIndex) {
        next = next.append(last())
        lastUnitIndex++
    }

    val nextStr = next.toString()

    if (lastNumber) lastUnitIndex = length

    return when {
        nextStr.matches(variableRegex) -> Variable(nextStr)
        nextStr.isNumber() -> Number(nextStr.toInt())
        next.matches(operatorRegex) -> Operator.get(nextStr)
        else -> null
    }
}

private infix fun Char.same(other: Char): Boolean {
    return (toString() + other).matches(Regex("\\d+|[+-]+"))
}

/**
 * From infix to postfix
 */
private fun infixToPost(expression: List<ExpressionUnit>): List<ExpressionUnit> {
    val result = Stack<ExpressionUnit>()
    val operator = Stack<ExpressionUnit>()

    for (unit in expression) {
        when (unit) {
            is Number -> result.push(unit)
            is Variable -> result.push(unit.toNumber())
            is Operator -> {
                if (unit.isBracket()) {
                    if (unit.literal == '(') {
                        operator.push(unit)
                    } else {
                        var operate = operator.pop()

                        while (operate.literal != '(') {
                            result.push(operate)
                            if (operator.empty()) {
                                break
                            } else {
                                operate = operator.pop()
                            }
                        }
                    }
                } else {
                    if (operator.empty() || operator.peek().literal == '(') {
                        operator.push(unit)
                    } else {
                        if (unit > operator.peek()) {
                            operator.push(unit)
                        } else {
                            while (operator.isNotEmpty() && unit <= operator.peek()) {
                                result.push(operator.pop())
                                if (operator.isNotEmpty() && operator.peek().literal == '(') break
                            }
                            operator.push(unit)
                        }
                    }
                }
            }
        }
    }

    while (operator.isNotEmpty()) {
        result.push(operator.pop())
    }

    return result.toList()
}

private fun postfixCalculate(expression: List<ExpressionUnit>): BigInteger {
    val calculate = Stack<ExpressionUnit>()

    for (unit in expression) {
        when (unit) {
            is Number -> calculate.push(unit)
            is Operator -> calculate.push(unit.calc(calculate.pop(), calculate.pop()))
        }
    }

    return calculate.pop().literal as BigInteger
}

/**
 * For assignment statement, check whether the user's input is a number or a variable
 */
private fun String.isNumber(): Boolean {
    if (this == "") return false

    for (c in this) {
        if (!c.isDigit()) return false
    }

    return true
}

/**
 * This is a base unit in a expression, could be [Number], [Operator], [Variable]
 * I use a list of [ExpressionUnit] to represents an expression
 */
private open class ExpressionUnit(val priority: Int, open val literal: Any)

/**
 * 10, 20, 30...
 */
private class Number(override val literal: BigInteger) : ExpressionUnit(0, literal) {
    operator fun plus(other: Number) = literal + other.literal

    operator fun minus(other: Number) = literal - other.literal

    operator fun times(other: Number) = literal * other.literal

    operator fun div(other: Number) = literal / other.literal

    infix fun pow(other: Number): BigInteger = literal.pow(other.literal.toInt())
}

/**
 * a, b, c, test, age...
 */
private class Variable(override val literal: String) : ExpressionUnit(0, literal) {
    fun toNumber() = Number(variables[literal]!!)
}

/**
 * +, -, *, /, ^, (, )...
 */
private class Operator private constructor(priority: Int, override val literal: Char) :
    ExpressionUnit(priority, literal) {
    companion object {
        // For unexpected situation
        val NONE = Operator(-1, '\u0000')
        val ADDITION = Operator(1, '+')
        val SUBTRACTION = Operator(1, '-')
        val MULTIPLICATION = Operator(2, '*')
        val DIVISION = Operator(2, '/')
        val EXPONENTIATION = Operator(3, '^')
        val LEFT_BRACKET = Operator(4, '(')
        val RIGHT_BRACKET = Operator(4, ')')

        /**
         * Convert "+++", "---", "+-+" to '+' or '-'
         */
        fun checkPlusMinus(operatorSequence: String): Operator {
            return if (operatorSequence.count { it == '-' } % 2 == 0) {
                ADDITION
            } else {
                SUBTRACTION
            }
        }

        fun get(operator: String): Operator {
            return when (if (operator.length > 1) checkPlusMinus(operator).literal else operator[0]) {
                '+' -> ADDITION
                '-' -> SUBTRACTION
                '*' -> MULTIPLICATION
                '/' -> DIVISION
                '^' -> EXPONENTIATION
                '(' -> LEFT_BRACKET
                ')' -> RIGHT_BRACKET
                else -> NONE
            }
        }
    }

    fun isBracket() = literal in arrayOf('(', ')')

    fun calc(first: ExpressionUnit, second: ExpressionUnit): Number {
        first as Number
        second as Number
        val result = when (literal) {
            '+' -> second + first
            '-' -> second - first
            '*' -> second * first
            '/' -> second / first
            else -> second pow first
        }

        return Number(result)
    }

    /**
     * Compare this operator's priority
     */
    operator fun compareTo(other: ExpressionUnit) = priority - other.priority
}
