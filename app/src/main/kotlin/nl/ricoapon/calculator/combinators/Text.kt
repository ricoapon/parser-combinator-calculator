package nl.ricoapon.calculator.combinators

import nl.ricoapon.calculator.types.ParseResult.Failure
import nl.ricoapon.calculator.types.ParseResult.Success
import nl.ricoapon.calculator.types.Parser

fun character(needle: Char): Parser<Char> = Parser { input ->
    if (input.startsWith(needle)) {
        Success(needle, input.drop(1))
    } else {
        Failure("Could not find character '$needle'. Instead it found '${input.firstOrNull()}'.")
    }
}

fun characters(s: String): Parser<String> =
    map(
        sequence(
            *(s.map { character(it) }.toTypedArray())
        )
    ) {
        it.joinToString(separator = "")
    }

fun digit(): Parser<Int> = Parser func@{ input ->
    if (input.isEmpty()) {
        return@func Failure("Tried to find digit, but found nothing")
    }

    val d = input.first()
    if (!d.isDigit()) {
        return@func Failure("Expected a digit, but found non-digit character $d.")
    }

    Success(d.digitToInt(), input.drop(1))
}
