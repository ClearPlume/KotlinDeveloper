fun solution(setSource: Set<String>, arraySource: Array<String>): MutableSet<String> {
    return setSource.toMutableSet().apply { addAll(arraySource) }
}