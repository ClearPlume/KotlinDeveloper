fun solution(numbers: List<Int>) {
    // put your code here
    println(numbers.filter { it % 2 == 0 }.joinToString(" "))
}