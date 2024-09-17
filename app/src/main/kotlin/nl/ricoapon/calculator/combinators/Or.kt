package nl.ricoapon.calculator.combinators

import nl.ricoapon.calculator.types.ParseResult.Failure
import nl.ricoapon.calculator.types.Parser

infix fun <T> Parser<T>.or(p: Parser<T>): Parser<T> = Parser { input ->
    this.parse(input).ifFailure { error1 ->
        p.parse(input).ifFailure { error2 ->
            Failure("Could not find one of the following options:\n$error1\n$error2")
        }
    }
}

fun <T> choice(vararg parsers: Parser<T>): Parser<T> =
    parsers.reduce { p1, p2 ->
        p1 or p2
    }
