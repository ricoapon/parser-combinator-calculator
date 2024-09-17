package nl.ricoapon.calculator.combinators

import kotlin.test.Test

class TextTest {
    @Test
    fun characterWorks() {
        val p = character('A')
        p.parse("A").isSuccess('A', "")
        p.parse("AB").isSuccess('A', "B")
        p.parse("B").isFailure()
    }

    @Test
    fun digitWorks() {
        val p = digit()
        p.parse("1").isSuccess(1, "")
        p.parse("82").isSuccess(8, "2")
        p.parse("A12").isFailure()
        p.parse(".").isFailure()
    }

    @Test
    fun charactersWorks() {
        val p = characters("AB")
        p.parse("AB").isSuccess("AB", "")
        p.parse("ABC").isSuccess("AB", "C")
        p.parse("BC").isFailure()
        p.parse("A").isFailure()
    }
}