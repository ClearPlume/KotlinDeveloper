fun solution(elements: MutableSet<Int>, element: Int): MutableSet<Int> {
    return if (element in elements) mutableSetOf() else elements
}