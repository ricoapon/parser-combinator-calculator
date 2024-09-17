package nl.ricoapon.calculator.combinators

import kotlin.test.Test

class AndTest {
    @Test
    fun andOnDifferentTypesWork() {
        val p = character('A') and digit()
        p.parse("A12").isSuccess('A' to 1, "2")
        p.parse("AB").isFailure()
        p.parse("B1").isFailure()
    }

    @Test
    fun andOnSameTypesWork() {
        val p = character('A') and character('B')
        p.parse("ABCD").isSuccess('A' to 'B', "CD")
        p.parse("A").isFailure()
        p.parse("BCD").isFailure()
    }

    @Test
    fun sequenceWorks() {
        val p = sequence(
            character('A'),
            character('B'),
            character('C')
        )

        p.parse("ABCD").isSuccess(listOf('A', 'B', 'C'), "D")
        p.parse("ACBD").isFailure()
        p.parse("AB").isFailure()
    }
}