
import org.hyperskill.hstest.testcase.CheckResult


class Task1Test : GeneratorTest<Unit>() {

    override fun generate() = listOf(
            testCase(Unit, "")
    )

    override fun check(reply: String, clue: Unit): CheckResult {
        val badge = " _____________\n" +
                "| Hyper Skill |\n" +
                " •••••••••••••"

        if (reply.trim('\n', ' ') != badge.trim(' ')) {
            return CheckResult.wrong(
                    "Your output is wrong! " +
                            "See example #1.")
        } else {
            return CheckResult.correct()
        }

    }
}

