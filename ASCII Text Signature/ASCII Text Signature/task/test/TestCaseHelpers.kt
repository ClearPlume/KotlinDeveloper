import org.hyperskill.hstest.testcase.TestCase


/** Default testCase. */
fun <T> testCase(attach: T, input: String) = TestCase<T>().apply {
    setInput(input)
    setAttach(attach)
}
