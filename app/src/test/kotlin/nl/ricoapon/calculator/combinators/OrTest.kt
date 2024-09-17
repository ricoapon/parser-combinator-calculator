package nl.ricoapon.calculator.combinators

import kotlin.test.Test

class OrTest {
    @Test
    fun orWorks() {
        val p = character('A') or character('B')
        p.parse("AB").isSuccess('A', "B")
        p.parse("BD").isSuccess('B', "D")
        p.parse("C").isFailure()
    }

    @Test
    fun choiceWorks() {
        val p = choice(
            character('A'),
            character('B'),
            character('C'),
        )
        p.parse("AB").isSuccess('A', "B")
        p.parse("BD").isSuccess('B', "D")
        p.parse("C").isSuccess('C', "")
        p.parse("DE").isFailure()
    }
}