// The array numbers already exists. Write only exchange actions here.
if (numbers.size > 1) {
    val tmp = numbers.first()
    numbers[0] = numbers.last()
    numbers[numbers.lastIndex] = tmp
}