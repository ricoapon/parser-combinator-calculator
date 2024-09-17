package nl.ricoapon.calculator.types

fun interface Parser<T> {
    fun parse(input: String): ParseResult<T>
}
