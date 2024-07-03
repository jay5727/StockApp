package com.jay.stockapp.util

import com.jay.stockapp.network.model.UserHolding

/**
 * Holding Mapper for calculations
 */
class HoldingMapper {

    /**
     * Returns the total current value of the holdings
     */
    fun getCurrentValue(holdingList: List<UserHolding>) = holdingList.sumOf { getCurrentValue(it) }

    /**
     * Returns the total investment value of the holdings
     */
    fun getTotalInvestment(holdingList: List<UserHolding>) =
        holdingList.sumOf { getInvestmentValue(it) }

    /**
     * Returns the individual stock current value of the holdings
     */
    fun getCurrentValue(holding: UserHolding?) =
        holding?.ltp.orZero() * holding?.quantity.orZero()

    /**
     * Returns the individual stock invested value of the holdings
     */
    fun getInvestmentValue(holding: UserHolding?) =
        holding?.avgPrice?.orZero()?.times(holding.quantity.orZero()).orZero()

    /**
     * Returns the day pnl of all the holdings
     */
    fun getDayPnL(list: List<UserHolding>) = list.sumOf {
        (it.close.orZero() - it.ltp.orZero()) * it.quantity.orZero()
    }

    /**
     * Returns the individual stock pnl of the holdings
     */
    fun getIndividualStockPNL(holding: UserHolding): Double {
        val currentValue: Double = getCurrentValue(holding).orZero()
        val investmentValue: Double = getInvestmentValue(holding).orZero()
        return (currentValue - investmentValue).getRoundUpto2Decimals()
    }


    /**
     * Returns the todays total pnl of all the holdings based on LTP-AvgPrice
     */
    fun getTodaysPnl(list: List<UserHolding>): Double {
        return list.sumOf { (it.ltp.orZero() - it.avgPrice.orZero()) * it.quantity.orZero() }
    }

    /**
     * Returns the closing pnl of all the holdings based on Close-AvgPrice
     */
    fun getClosingPnl(list: List<UserHolding>): Double {
        return list.sumOf { (it.close.orZero() - it.avgPrice.orZero()) * it.quantity.orZero() }
    }

    /**
     * Returns the percentage change in pnl of all the holdings
     */
    fun getPercentageChange(list: List<UserHolding>): Double {
        val totalPnlClose = getClosingPnl(list)
        val totalPnlToday = getTodaysPnl(list)
        val percentageChange = if (totalPnlClose != 0.0) {
            ((totalPnlToday - totalPnlClose) / totalPnlClose) * 100
        } else {
            0.0
        }
        return percentageChange.getRoundUpto2Decimals()
    }
}