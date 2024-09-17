package nl.ricoapon.calculator.combinators

import nl.ricoapon.calculator.types.ParseResult.Success
import nl.ricoapon.calculator.types.Parser

infix fun <S, T> Parser<S>.and(p: Parser<T>): Parser<Pair<S, T>> = Parser { input ->
    this.parse(input).ifSuccess { first, intermediate ->
        p.parse(intermediate).ifSuccess { second, rest ->
            Success(first to second, rest)
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

data class Chain<T, S>(val terms: List<T>, val separators: List<S>) {
    companion object {
        // Technical issue with empty lists which the JVM cannot recognize (typing is lost).
        // This trick fixes that.
        private val emptyChain: Chain<*, *> = Chain<Any?, Any?>(emptyList(), emptyList())

        @Suppress("UNCHECKED_CAST")
        fun <T, S> empty(): Chain<T, S> = emptyChain as Chain<T, S>
    }
}

fun <S, T> chain(p: Parser<T>, separator: Parser<S>): Parser<Chain<T, S>> =
    map((p and (separator and p).zeroOrMore())) {
        val listT = listOf(it.first) + it.second.map { x -> x.second }
        val listS = it.second.map { x -> x.first }
        Chain(listT, listS)
    } or Parser { input -> Success(Chain.empty(), input) }
