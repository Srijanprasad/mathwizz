package com.mathsprint.game.domain.model

import kotlin.math.abs

/**
 * Immutable representation of a rational number (fraction) to guarantee
 * exact mathematical comparisons without floating-point inaccuracies.
 */
data class Rational(
    val numerator: Long,
    val denominator: Long = 1L
) : Comparable<Rational> {

    init {
        require(denominator != 0L) { "Denominator cannot be zero." }
    }

    val reducedNumerator: Long
    val reducedDenominator: Long

    init {
        val gcd = gcd(abs(numerator), abs(denominator))
        val sign = if (denominator < 0) -1L else 1L
        reducedNumerator = (numerator / gcd) * sign
        reducedDenominator = abs(denominator) / gcd
    }

    operator fun plus(other: Rational): Rational {
        val num = reducedNumerator * other.reducedDenominator + other.reducedNumerator * reducedDenominator
        val den = reducedDenominator * other.reducedDenominator
        return Rational(num, den)
    }

    operator fun minus(other: Rational): Rational {
        val num = reducedNumerator * other.reducedDenominator - other.reducedNumerator * reducedDenominator
        val den = reducedDenominator * other.reducedDenominator
        return Rational(num, den)
    }

    operator fun times(other: Rational): Rational {
        return Rational(
            reducedNumerator * other.reducedNumerator,
            reducedDenominator * other.reducedDenominator
        )
    }

    override fun compareTo(other: Rational): Int {
        val left = reducedNumerator * other.reducedDenominator
        val right = other.reducedNumerator * reducedDenominator
        return left.compareTo(right)
    }

    override fun toString(): String {
        return if (reducedDenominator == 1L) {
            reducedNumerator.toString()
        } else {
            "$reducedNumerator/$reducedDenominator"
        }
    }

    companion object {
        fun of(n: Long, d: Long = 1L) = Rational(n, d)

        private tailrec fun gcd(a: Long, b: Long): Long {
            return if (b == 0L) a else gcd(b, a % b)
        }
    }
}
