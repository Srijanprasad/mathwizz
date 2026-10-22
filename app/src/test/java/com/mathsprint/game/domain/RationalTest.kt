package com.mathsprint.game.domain

import com.mathsprint.game.domain.model.Rational
import org.junit.Assert.*
import org.junit.Test

class RationalTest {

    @Test
    fun testReduction() {
        val r = Rational(4, 8)
        assertEquals(1L, r.reducedNumerator)
        assertEquals(2L, r.reducedDenominator)
        assertEquals("1/2", r.toString())
    }

    @Test
    fun testWholeNumberToString() {
        val r = Rational(6, 2)
        assertEquals(3L, r.reducedNumerator)
        assertEquals(1L, r.reducedDenominator)
        assertEquals("3", r.toString())
    }

    @Test
    fun testAddition() {
        val r1 = Rational(1, 4)
        val r2 = Rational(2, 4)
        val sum = r1 + r2
        assertEquals("3/4", sum.toString())

        val r3 = Rational(1, 2)
        val r4 = Rational(1, 3)
        val sum2 = r3 + r4
        assertEquals("5/6", sum2.toString())
    }

    @Test
    fun testSubtraction() {
        val r1 = Rational(3, 4)
        val r2 = Rational(1, 4)
        val diff = r1 - r2
        assertEquals("1/2", diff.toString())
    }

    @Test
    fun testMultiplication() {
        val r1 = Rational(2, 3)
        val r2 = Rational(3, 4)
        val prod = r1 * r2
        assertEquals("1/2", prod.toString())
    }

    @Test(expected = IllegalArgumentException::class)
    fun testZeroDenominatorThrows() {
        Rational(5, 0)
    }

    @Test
    fun testComparison() {
        val r1 = Rational(1, 2)
        val r2 = Rational(2, 3)
        assertTrue(r1 < r2)
        assertTrue(r2 > r1)
        assertEquals(0, Rational(2, 4).compareTo(Rational(1, 2)))
    }
}
