package nl.ricoapon.calculator.combinators

import nl.ricoapon.calculator.types.ParseResult
import nl.ricoapon.calculator.types.Parser

infix fun <S, T> Parser<S>.and(p: Parser<T>): Parser<Pair<S, T>> = Parser { input ->
    this.parse(input).ifSuccess { first, intermediate ->
        p.parse(intermediate).ifSuccess { second, rest ->
            ParseResult.Success(first to second, rest)
        }
    }
}


// If we have an "and" where all the types of the parsers are identical, we can merge them together with typing.
// We make them all parsers of List<T>, where we can use "and" on these types by just adding the two lists.
fun <T> sequence(vararg parsers: Parser<T>): Parser<List<T>> =
    parsers.map { map(it) { x -> listOf(x) } }
        .reduce { p1, p2 ->
            map(p1 and p2) { (first, second) -> first + second }
        }
