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

    @Test
    fun chainWorks() {
        val p = chain(digit(), character('+'))

        p.parse("1").isSuccess(Chain(listOf(1), listOf()), "")
        p.parse("1+2").isSuccess(Chain(listOf(1, 2), listOf('+')), "")
        p.parse("1+2+3").isSuccess(Chain(listOf(1, 2, 3), listOf('+', '+')), "")
    }
}