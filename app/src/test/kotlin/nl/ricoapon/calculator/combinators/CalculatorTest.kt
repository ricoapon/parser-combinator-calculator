package nl.ricoapon.calculator.combinators

import nl.ricoapon.calculator.types.ParseResult
import nl.ricoapon.calculator.types.ParseResult.Success
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CalculatorTest {
    private val calculator = expression()

    private fun ParseResult<Double>.hasResult(result: Int) {
        hasResult(result.toDouble())
    }

    private fun ParseResult<Double>.hasResult(result: Double) {
        assertTrue(this is Success, "Got failure: $this")
        assertEquals(result, this.data)
        assertEquals("", this.rest, "Leftover text should be empty")
    }

    @Nested
    inner class CombinedOperators {
        @Test
        fun plusAndMinus() {
            calculator.parse("4+5-9").hasResult(0)
            calculator.parse("4-9+5").hasResult(0)
        }
    }

    @Nested
    inner class SingleOperator {
        @Test
        fun plusWorks() {
            calculator.parse("4+56").hasResult(60)
            calculator.parse("4+5+2").hasResult(11)
        }

        @Test
        fun minusWorks() {
            calculator.parse("10-2").hasResult(8)
            calculator.parse("10-1-2").hasResult(7)
        }

        @Test
        fun timesWorks() {
            calculator.parse("10*2").hasResult(20)
            calculator.parse("10*3*4").hasResult(120)
        }

        @Test
        fun divideWorks() {
            calculator.parse("1/0").hasResult(Double.POSITIVE_INFINITY)
            calculator.parse("6/2/3").hasResult(1)
        }

        @Test
        fun powerWorks() {
            calculator.parse("2^3").hasResult(8)
            calculator.parse("2^3^2").hasResult(512)
        }
    }
}