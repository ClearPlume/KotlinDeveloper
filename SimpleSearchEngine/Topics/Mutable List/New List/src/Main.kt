fun solution(numbers: List<Int>, number: Int): MutableList<Int> {
    // put your code here
    val mutableList = numbers.toMutableList()
    mutableList.add(number)
    return mutableList
}