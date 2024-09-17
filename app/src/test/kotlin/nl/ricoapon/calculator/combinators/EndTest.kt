package nl.ricoapon.calculator.combinators

import kotlin.test.Test

class EndTest {
    @Test
    fun endOfInputWorks() {
        val p = character('A') and endOfInput()
        p.parse("A").isSuccess('A' to Unit, "")
        p.parse("AB").isFailure()
    }

    fun endWorks() {
        val p = character('A').end()
        p.parse("A").isSuccess('A', "")
        p.parse("AB").isFailure()
    }
}