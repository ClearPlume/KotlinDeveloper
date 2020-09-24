import java.util.*

fun main() {
    val word = Scanner(System.`in`).next()
    val vowels = charArrayOf('a', 'e', 'i', 'o', 'u', 'y')
    var num = 0
    var lastPos = -1

    for (i in word.indices) {
        if (i < lastPos) {
            continue
        } else if (word.length > 1 && i == word.lastIndex) {
            if (word.last() == word[i - 1]) {
                break
            }
        }
        var iNum = 0

        for (j in i..word.lastIndex) {
            lastPos = j

            if (vowels.contains(word[i]) == vowels.contains(word[j])) {
                iNum++
            } else {
                break
            }
        }
        if (iNum >= 3) {
            num += iNum / 2 - if (iNum % 2 == 0) 1 else 0
        }
    }
    print(num)
}