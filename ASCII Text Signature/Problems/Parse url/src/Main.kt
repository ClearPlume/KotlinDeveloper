fun main() {
    val input = java.util.Scanner(System.`in`)

    val parameters = input.next().split('?').toTypedArray()[1]
    var hasPass = false
    var pass = ""

    for (parameter in parameters.split('&')) {
        val param = parameter.split('=').toTypedArray()
        if (param[0] == "pass") {
            hasPass = true
            pass = param[1]
        }
        if (param[1] == "") {
            param[1] = "not found"
        }
        println("${param[0]} : ${param[1]}")
    }

    if (hasPass) {
        println("password : $pass")
    }
}