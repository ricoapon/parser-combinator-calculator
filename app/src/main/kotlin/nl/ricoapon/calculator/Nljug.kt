package nl.ricoapon.calculator

typealias NljugParser<T> = (String) -> Pair<T, String>?

// This contains all code examples from the NLJUG magazine article.
fun main() {
    val p = Nljug().expression()
    println(p("100000+1000+10"))
    // Issue is the minus that doesn't work well.
    println(p("100000-1000-10"))
}

class Nljug {
    fun character(needle: Char): NljugParser<Char> = { input ->
        if (input.startsWith(needle)) {
            needle to input.drop(1)
        } else {
            null
        }
    }

    fun <T> or(p: NljugParser<T>, q: NljugParser<T>): NljugParser<T> = { input ->
        p(input) ?: q(input)
    }

    fun <S, T> and(p: NljugParser<S>, q: NljugParser<T>): NljugParser<Pair<S, T>> = { input ->
        p(input)?.let { (first, intermediate) ->
            q(intermediate)?.let { (second, rest) ->
                (first to second) to rest
            }
        }
    }

    fun <S, T> map(p: NljugParser<S>, transform: (S) -> T): NljugParser<T> = { input ->
        p(input)?.let { (result, rest) -> transform(result) to rest }
    }

    fun <T> star(p: NljugParser<T>): NljugParser<List<T>> = { input ->
        val result = p(input)
        if (result != null) {
            val (match, intermediate) = result
            val recursiveResult = star(p)(intermediate)
            if (recursiveResult == null) {
                (listOf(match) to intermediate)
            } else {
                (listOf(match) + recursiveResult.first) to recursiveResult.second
            }
        } else {
            emptyList<T>() to input
        }
    }

    fun <T> starLazy(p: NljugParser<T>): NljugParser<List<T>> =
        or(
            map(and(p, lazy { starLazy(p) })) { (r, rs) -> listOf(r) + rs },
            succeed(emptyList())
        )

    fun <T> lazy(nljugParserProducer: () -> NljugParser<T>): NljugParser<T> = { input ->
        nljugParserProducer()(input)
    }

    fun <T> succeed(value: T): NljugParser<T> = { input ->
        value to input
    }

    fun <T> fail(): NljugParser<T> = { _ ->
        null
    }


    // Binary parser.
    fun leadingDigit() = map(character('1')) { _ -> 1 }

    fun digit() = or(
        map(character('0')) { _ -> 0 },
        map(character('1')) { _ -> 1 }
    )

    fun number(): NljugParser<Int> = map(and(leadingDigit(), star(digit()))) { (digit, digits) ->
        (listOf(digit) + digits).fold(0) { acc, d -> 2 * acc + d }
    }

    fun operator(): NljugParser<(Int, Int) -> Int> = or(
        map(character('+')) { _ -> Int::plus },
        map(character('-')) { _ -> Int::minus }
    )

    fun expression(): NljugParser<Int> = or(
        map(
            and(
                number(),
                and(
                    operator(),
                    lazy(::expression),
                )
            )
        ) { (first, second) ->
            val (op, right) = second
            op(first, right)
        },
        number()
    )
}
