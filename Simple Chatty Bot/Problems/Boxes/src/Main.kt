import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val box1 = ArrayList<Int>()
    val box2 = ArrayList<Int>()

    scanner.nextLine().split(' ').forEach { value -> box1.add(value.toInt()) }
    scanner.nextLine().split(' ').forEach { value -> box2.add(value.toInt()) }

    box1.sort()
    box2.sort()

    if (box1[0] == box2[0] && box1[1] == box2[1] && box1[2] == box2[2]) {
        println("Box 1 = Box 2")
    } else if (box1[0] >= box2[0] && box1[1] >= box2[1] && box1[2] >= box2[2]) {
        println("Box 1 > Box 2")
    } else if (box1[0] <= box2[0] && box1[1] <= box2[1] && box1[2] <= box2[2]) {
        println("Box 1 < Box 2")
    } else {
        println("Incomparable")
    }
}