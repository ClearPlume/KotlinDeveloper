fun solution(products: List<String>, product: String) {
    // put your code here
    products.forEachIndexed { index, item ->
        if (item == product) {
            print(index)

            if (index < products.lastIndex) {
                print(' ')
            }
        }
    }
}