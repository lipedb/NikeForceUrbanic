package com.kforce.urbanic.util

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Fail] or [Success].
 * FP Convention dictates that [Fail] is used for "failure"
 * and [Success] is used for "success".
 *
 * @see Fail
 * @see Success
 */
sealed class Either<out L, out R> {
    /** * Represents the fail side of [Either] class which by convention is a "Failure". */
    data class Fail<out L>(val a: L) : Either<L, Nothing>()

    /** * Represents the success side of [Either] class which by convention is a "Success". */
    data class Success<out R>(val b: R) : Either<Nothing, R>()

    /**
     * Returns true if this is a Success, false otherwise.
     * @see Success
     */
    val isSuccess get() = this is Success<R>

    /**
     * Returns true if this is a Fail, false otherwise.
     * @see Fail
     */
    val isFail get() = this is Fail<L>

    /**
     * Creates a Fail type.
     * @see Fail
     */
    fun <L> fail(a: L) = Either.Fail(a)

    /**
     * Creates a Fail type.
     * @see Success
     */
    fun <R> success(b: R) = Either.Success(b)

    /**
     * Applies fnL if this is a Fail or fnR if this is a Success.
     * @see Fail
     * @see Success
     */
    fun fold(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Fail -> fnL(a)
            is Success -> fnR(b)
        }
}

/**
 * Composes 2 functions
 * See <a href="https://proandroiddev.com/kotlins-nothing-type-946de7d464fb">Credits to Alex Hart.</a>
 */
fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

/**
 * Success-biased flatMap() FP convention which means that Success is assumed to be the default case
 * to operate on. If it is Fail, operations like map, flatMap, ... return the Fail value unchanged.
 */
fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Either.Fail -> Either.Fail(a)
        is Either.Success -> fn(b)
    }

/**
 * Success-biased map() FP convention which means that Success is assumed to be the default case
 * to operate on. If it is Fail, operations like map, flatMap, ... return the Fail value unchanged.
 */
fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> = this.flatMap(fn.c(::success))

/** Returns the value from this `Success` or the given argument if this is a `Fail`.
 *  Success(12).getOrElse(17) RETURNS 12 and Fail(12).getOrElse(17) RETURNS 17
 */
fun <L, R> Either<L, R>.getOrElse(value: R): R =
    when (this) {
        is Either.Fail -> value
        is Either.Success -> b
    }

/**
 * Fail-biased onFailure() FP convention dictates that when this class is Fail, it'll perform
 * the onFailure functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
fun <L, R> Either<L, R>.onFailure(fn: (failure: L) -> Unit): Either<L, R> =
    this.apply { if (this is Either.Fail) fn(a) }

/**
 * Success-biased onSuccess() FP convention dictates that when this class is Success, it'll perform
 * the onSuccess functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
fun <L, R> Either<L, R>.onSuccess(fn: (success: R) -> Unit): Either<L, R> =
    this.apply { if (this is Either.Success) fn(b) }