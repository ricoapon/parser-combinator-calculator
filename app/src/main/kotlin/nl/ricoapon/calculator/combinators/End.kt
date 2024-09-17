package nl.ricoapon.calculator.combinators

import nl.ricoapon.calculator.types.ParseResult.Failure
import nl.ricoapon.calculator.types.ParseResult.Success
import nl.ricoapon.calculator.types.Parser

fun endOfInput(): Parser<Unit> = Parser { input ->
    if (input.isNotEmpty()) {
        Failure("Input was expected to be empty, but the following was still left to parse: $input")
    } else {
        Success(Unit, input)
    }
}

fun <T> Parser<T>.end(): Parser<T> = map(this and endOfInput()) { it.first }
