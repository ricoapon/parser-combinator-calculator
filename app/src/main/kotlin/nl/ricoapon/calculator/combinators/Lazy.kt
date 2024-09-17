package nl.ricoapon.calculator.combinators

import nl.ricoapon.calculator.types.Parser

// Needed to create recursive expressions.
fun <T> lazy(parserProducer: () -> Parser<T>): Parser<T> = Parser { input ->
    parserProducer().parse(input)
}
