package nl.ricoapon.calculator.combinators

import kotlin.test.Test

class RepeatTest {
    @Test
    fun zeroOrMoreWorks() {
        val p = digit().zeroOrMore()
        p.parse("ab").isSuccess(listOf(), "ab")
        p.parse("1ab").isSuccess(listOf(1), "ab")
        p.parse("123ab").isSuccess(listOf(1, 2, 3), "ab")
    }

    @Test
    fun oneOrMoreWorks() {
        val p = digit().oneOrMore()
        p.parse("ab").isFailure()
        p.parse("1ab").isSuccess(listOf(1), "ab")
        p.parse("123ab").isSuccess(listOf(1, 2, 3), "ab")
    }

    @Test
    fun optionalWorks() {
        val p = digit().optional()
        p.parse("a").isSuccess(null, "a")
        p.parse("1a").isSuccess(1, "a")
    }
}