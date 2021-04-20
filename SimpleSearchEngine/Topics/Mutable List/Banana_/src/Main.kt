fun solution(strings: MutableList<String>, str: String) =
    strings.apply { replaceAll { if (it == str) "Banana" else it } }