package com.jay.stockapp.util

import com.jay.stockapp.network.model.UserHolding
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkObject
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [HoldingMapper]
 */
class HoldingMapperTest {

    private lateinit var holdingMapper: HoldingMapper

    @Before
    fun setUp() {
        holdingMapper = HoldingMapper()
    }
    @Test
    fun `getFormattedString returns formatted string`() {
        val value = 1234.56
        val expected = "₹ 1,234.56"
        val result = value.getFormattedString()
        assertEquals(expected, result)
    }
    @Test
    fun `getCurrentValue single returns correct current value`() {
        val userHolding = mockk<UserHolding>()
        every { userHolding.ltp } returns 150.0
        every { userHolding.quantity } returns 10

        val expected = 1500.0
        val result = holdingMapper.getCurrentValue(userHolding)
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `getTotalInvestment returns correct total investment value`() {
        val userHolding1 = mockk<UserHolding>()
        val userHolding2 = mockk<UserHolding>()
        every { userHolding1.avgPrice } returns 100.0
        every { userHolding1.quantity } returns 10
        every { userHolding2.avgPrice } returns 200.0
        every { userHolding2.quantity } returns 5

        val holdings = listOf(userHolding1, userHolding2)

        val expected = 1000.0 + 1000.0 // 100 * 10 + 200 * 5
        val result = holdingMapper.getTotalInvestment(holdings)
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `getPNL returns correct PNL`() {
        val userHolding = mockk<UserHolding>()
        every { userHolding.ltp } returns 150.0
        every { userHolding.quantity } returns 10
        every { userHolding.avgPrice } returns 100.0

        val expected = 500.0
        val result = holdingMapper.getIndividualStockPNL(userHolding)
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `getCurrentValue returns correct current value`() {
        val userHolding = mockk<UserHolding>()
        every { userHolding.ltp } returns 150.0
        every { userHolding.quantity } returns 10

        val expected = 1500.0
        val result = holdingMapper.getCurrentValue(listOf(userHolding) )
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `getInvestmentValue returns correct investment value`() {
        val userHolding = mockk<UserHolding>()
        every { userHolding.avgPrice } returns 100.0
        every { userHolding.quantity } returns 10

        val expected = 1000.0
        val result = holdingMapper.getInvestmentValue(userHolding)
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `getDayPnL returns correct day PnL`() {
        val userHolding = mockk<UserHolding>()
        every { userHolding.close } returns 200.0
        every { userHolding.ltp } returns 150.0
        every { userHolding.quantity } returns 10
        val holdings = listOf(userHolding)

        val expected = 500.0
        val result = holdingMapper.getDayPnL(holdings)
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `getTodaysPnL returns correct todays PnL`() {
        val userHolding = mockk<UserHolding>()
        every { userHolding.ltp } returns 150.0
        every { userHolding.avgPrice } returns 100.0
        every { userHolding.quantity } returns 10
        val holdings = listOf(userHolding)

        val expected = 500.0
        val result = holdingMapper.getTodaysPnl(holdings)
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `getClosingPnL returns correct closing PnL`() {
        val userHolding = mockk<UserHolding>()
        every { userHolding.close } returns 200.0
        every { userHolding.avgPrice } returns 100.0
        every { userHolding.quantity } returns 10
        val holdings = listOf(userHolding)

        val expected = 1000.0
        val result = holdingMapper.getClosingPnl(holdings)
        assertEquals(expected, result, 0.01)
    }

    @Test
    fun `getPercentageChange returns correct percentage change`() {
        val userHolding = mockk<UserHolding>()
        every { userHolding.close } returns 200.0
        every { userHolding.ltp } returns 150.0
        every { userHolding.avgPrice } returns 100.0
        every { userHolding.quantity } returns 10
        val holdings = listOf(userHolding)

        val expected = -50.0
        val result = holdingMapper.getPercentageChange(holdings)
        assertEquals(expected, result, 0.01)
    }


    @After
    fun tearDown() {
        unmockkObject(holdingMapper)
    }
}