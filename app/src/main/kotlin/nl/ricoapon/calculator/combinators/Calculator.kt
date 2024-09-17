package nl.ricoapon.calculator.combinators

import nl.ricoapon.calculator.types.Parser
import kotlin.math.pow

val operator: Parser<(Double, Double) -> (Double)> = choice(
    map(character('+')) { _ -> Double::plus },
    map(character('-')) { _ -> Double::minus },
    map(character('*')) { _ -> Double::times },
    map(character('/')) { _ -> Double::div },
    map(character('^')) { _ -> Double::pow },
)

val number = map(digit().oneOrMore()) {
    it.joinToString("").toDouble()
}

fun expression(): Parser<Double> =
    map((number and operator) and lazy { expression() }) { (first, right) ->
        val (left, op) = first
        op(left, right)
    } or number
