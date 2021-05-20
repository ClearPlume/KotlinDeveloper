fun solution(elements: Set<String>, element: String): MutableSet<String> {
    return HashSet(elements - element)
}