fun containsKeyAndValue(map: Map<String, String>, value: String): Boolean {
    // put your code here
    return value in map && value in map.values
}