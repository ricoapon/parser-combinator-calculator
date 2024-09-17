package nl.ricoapon.calculator.combinators

import nl.ricoapon.calculator.types.ParseResult
import nl.ricoapon.calculator.types.Parser

fun <S, T> map(p: Parser<S>, transform: (S) -> T): Parser<T> = Parser { input ->
    p.parse(input).ifSuccess { result, rest -> ParseResult.Success(transform(result), rest) }
}
