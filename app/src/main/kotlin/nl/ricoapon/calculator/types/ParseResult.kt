package nl.ricoapon.calculator.types

sealed interface ParseResult<T> {
    data class Success<T>(val data: T, val rest: String): ParseResult<T>
    data class Failure<T>(val errorMessage: String): ParseResult<T>

    fun <S> ifSuccess(block: (T, String) -> ParseResult<S>): ParseResult<S> {
        if (this is Success) {
            return block(data, rest)
        }

        // This works, because Failure doesn't use "T" anywhere.
        @Suppress("UNCHECKED_CAST")
        return this as ParseResult<S>
    }

    fun ifFailure(block: (String) -> ParseResult<T>): ParseResult<T> {
        if (this is Failure) {
            return block(errorMessage)
        }
        return this
    }
}
