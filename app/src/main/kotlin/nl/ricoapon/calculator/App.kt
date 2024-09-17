package nl.ricoapon.calculator

import nl.ricoapon.calculator.combinators.expression
import nl.ricoapon.calculator.types.ParseResult.Failure
import nl.ricoapon.calculator.types.ParseResult.Success

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val expression = expression()
    while (true) {
        print("> ")
        val input = readlnOrNull() ?: break

        val result = expression.parse(input)
        if (result is Success) {
            println(result.data)
            if (result.rest.isNotEmpty()) {
                println("String left to parse: ${result.rest}")
            }
        } else if (result is Failure) {
            println("Failure: ${result.errorMessage}")
        }
    }
}
