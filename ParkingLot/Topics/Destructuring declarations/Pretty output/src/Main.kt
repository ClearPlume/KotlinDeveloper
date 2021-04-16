data class Comment(val id: Int, val body: String, val author: String)

fun printComments(commentsData: Array<Comment>) {
    // write you code here
    for (comment in commentsData) {
        val (_, body, author) = comment
        println("Author: $author; Text: $body")
    }
}