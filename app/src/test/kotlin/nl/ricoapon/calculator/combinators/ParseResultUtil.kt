package nl.ricoapon.calculator.combinators

import nl.ricoapon.calculator.types.ParseResult
import kotlin.test.assertEquals
import kotlin.test.assertTrue

fun <T> ParseResult<T>.isFailure() {
    assertTrue(this is ParseResult.Failure)
}

fun <T> ParseResult<T>.isSuccess(data: T, rest: String) {
    assertEquals(ParseResult.Success(data, rest), this)
}
