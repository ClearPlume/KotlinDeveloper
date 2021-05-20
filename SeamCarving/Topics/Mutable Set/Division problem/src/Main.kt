fun solution(first: Set<Int>, second: Set<Int>): Set<Int> {
    val secondSize = second.size
    return first.filter { it % secondSize == 0 }.toSet()
}