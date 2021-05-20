fun solution(first: Set<String>, second: Array<String>): Boolean {
    return first.containsAll(second.toList())
}