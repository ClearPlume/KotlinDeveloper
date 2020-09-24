fun main() {
    val str = java.util.Scanner(System.`in`).next()

    var singleChar = 0

    for (c in str) {
        var single = true
        for (test in str.substring(str.indexOf(c) + 1)) {
            if (test == c) {
                single = false
                break
            }
        }
        singleChar += if (single) 1 else 0
    }

    print(singleChar)
}