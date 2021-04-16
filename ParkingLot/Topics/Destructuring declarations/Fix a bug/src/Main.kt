class Article(val name: String, val pages: Int, val author: String) {
    operator fun component1() = name
    operator fun component2() = pages
    operator fun component3() = author
}

fun getArticleByName(articles: Array<Article>, name: String): Article? {
    var index = -1
    for ((title, pages, author) in articles) {
        index += 1
        if (title == name) return articles[index]
    }
    return null
}