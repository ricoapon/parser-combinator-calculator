package nl.ricoapon.calculator.combinators

import nl.ricoapon.calculator.types.ParseResult
import nl.ricoapon.calculator.types.ParseResult.Success
import nl.ricoapon.calculator.types.Parser

fun <T> Parser<T>.repeat(atLeast: Int, atMost: Int = -1): Parser<List<T>> = Parser { input ->
    if (atLeast < 0) {
        throw RuntimeException("Repeat argument 'atLeast' must be greater or equal to zero, not $atLeast.")
    }
    if (atLeast >= atMost && atMost != -1) {
        throw RuntimeException("Repeat argument 'atLeast' ($atLeast) must always be lower than 'atMost' ($atMost).")
    }

    var i = 0
    var previousResult: ParseResult<List<T>> = Success(emptyList(), input)
    while (atMost == -1 || i < atMost) {
        val nextResult = this.parse((previousResult as Success).rest)
        if (nextResult is ParseResult.Failure) {
            break
        }
        previousResult = Success(previousResult.data + (nextResult as Success).data, nextResult.rest)
        i++
    }

    if (i < atLeast) {
        throw RuntimeException("Parser should be repeated $atLeast times, but was repeated $i times at most.")
    }

    previousResult
}

fun <T> Parser<T>.zeroOrMore(): Parser<List<T>> = repeat(0)
fun <T> Parser<T>.oneOrMore(): Parser<List<T>> = repeat(1)
fun <T> Parser<T>.optional(): Parser<T?> = map(repeat(0, 1)) {
    if (it.isEmpty()) {
        null
    } else {
        it[0]
    }
}
