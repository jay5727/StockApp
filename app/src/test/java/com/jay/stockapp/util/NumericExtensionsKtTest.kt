package com.jay.stockapp.util

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Test for numeric extensions
 */
class NumericExtensionsKtTest {

    @Test
    fun `Int orZero should return zero when null`() {
        val value: Int? = null
        assertEquals(0, value.orZero())
    }

    @Test
    fun `Int orZero should return the value when not null`() {
        val value: Int? = 5
        assertEquals(5, value.orZero())
    }

    @Test
    fun `Double orZero should return zero when null`() {
        val value: Double? = null
        assertEquals(0.0, value.orZero(), 0.0)
    }

    @Test
    fun `Double orZero should return the value when not null`() {
        val value: Double? = 5.5
        assertEquals(5.5, value.orZero(), 0.0)
    }

    @Test
    fun `getRoundUpto2Decimals should round up correctly`() {
        val value: Double? = 2.345
        assertEquals(2.35, value.getRoundUpto2Decimals(), 0.0)
    }


    @Test
    fun `decimalPattern should return correct pattern`() {
        val value = "123.456"
        assertEquals("00", value.decimalPattern())
    }


    @Test
    fun `getFormattedString should return empty string when null`() {
        val value: Double? = null
        assertEquals("", value.getFormattedString())
    }
}