fun printFifthCharacter(input: String): String {
    if (input.length >= 5) {
        return "The fifth character of the entered word is ${input[4]}"
    }
    return "The input word is too short!"
}