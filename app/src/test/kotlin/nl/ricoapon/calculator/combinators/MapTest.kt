package nl.ricoapon.calculator.combinators

import kotlin.test.Test

class MapTest {
    @Test
    fun mapWorks() {
        val p = map(digit()) { i -> i.toString() }
        p.parse("1").isSuccess("1", "")
        p.parse("4a").isSuccess("4", "a")
        p.parse("a").isFailure()
    }
}