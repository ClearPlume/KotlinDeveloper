fun main() {
    val input = java.util.Scanner(System.`in`)

    val string = input.next()
    var res = ""
    var lastPos = -1

    for (i in string.indices) {
        if (i < lastPos) {
            continue
        } else if (string.length > 1 && i == string.lastIndex) {
            if (string.last() == string[i - 1]) {
                break
            }
        }

        var iNum = 0

        for (j in i..string.lastIndex) {
            lastPos = j
            if (string[i] == string[j]) {
                iNum++
            } else {
                break
            }
        }

        res += "${string[i]}$iNum"
    }

    print(res)
}