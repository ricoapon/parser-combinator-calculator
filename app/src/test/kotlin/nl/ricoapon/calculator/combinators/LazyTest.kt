package nl.ricoapon.calculator.combinators

import nl.ricoapon.calculator.types.Parser
import kotlin.test.Test

class LazyTest {
    @Test
    fun lazyWorks() {
        // This "calculates" the result of a sum that is always structured as "1+1+1+....+1".
        fun recursive(): Parser<Int> =
                map(
                    (character('1') and character('+')) and lazy { recursive() }) { (_, last) ->
                    last + 1
                } or map(character('1')) { _ -> 1}

        recursive().parse("1+1+1+1+1").isSuccess(5, "")
        recursive().parse("1").isSuccess(1, "")
        recursive().parse("+1").isFailure()
    }
}