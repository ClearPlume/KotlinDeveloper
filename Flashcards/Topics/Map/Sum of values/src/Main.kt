fun summator(map: Map<Int, Int>): Int {
    // put your code here
    return map.filterKeys { it % 2 == 0 }.values.sum()
}