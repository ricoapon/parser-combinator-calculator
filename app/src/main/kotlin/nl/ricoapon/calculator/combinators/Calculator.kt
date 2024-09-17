package nl.ricoapon.calculator.combinators

import nl.ricoapon.calculator.types.Parser

val number = map(digit().oneOrMore()) {
    it.joinToString("").toDouble()
}

// We define all operators as separate expressions based on their precedence. The lowest precedence will occur first, so
// that parsing on these are done last.
fun expression(): Parser<Double> = plusExpression().end()

fun plusExpression(): Parser<Double> = map(chain(minusExpression(), character('+'))) {
    if (it.terms.isEmpty()) {
        0.0
    } else {
        it.terms.reduce {d, acc ->
            d + acc
        }
    }
} or number

// Minus is left-associate, so we chain to the right.
fun minusExpression(): Parser<Double> = map(chain(number, character('-'))) {
    if (it.terms.isEmpty()) {
        0.0
    } else {
        it.terms.reduce {d, acc ->
            d - acc
        }
    }
} or number
