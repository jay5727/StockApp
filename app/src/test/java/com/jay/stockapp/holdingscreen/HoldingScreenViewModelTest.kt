package com.jay.stockapp.holdingscreen

import CoroutineTestRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.jay.stockapp.core.result.ResultState
import com.jay.stockapp.holdingscreen.repository.HoldingRepository
import com.jay.stockapp.holdingscreen.viewmodel.HoldingScreenViewModel
import com.jay.stockapp.network.model.HoldingResponseModel
import com.jay.stockapp.network.model.UserHolding
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for [HoldingScreenViewModel]
 */
class HoldingScreenViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    var data = InstantTaskExecutorRule()

    @MockK
    private lateinit var repository: HoldingRepository

    private lateinit var viewModel: HoldingScreenViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
        viewModel = HoldingScreenViewModel(coroutinesTestRule.testDispatcher, repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test fetch holding list then returns success`() = runTest (coroutinesTestRule.testDispatcher) {
        val modelResponse = parseHoldingResponseJson(JSON_RESPONSE)
        val flowResponse = flow<ResultState<HoldingResponseModel>> {
            ResultState.Success(modelResponse)
        }
        coEvery { repository.fetchHoldingResponse() } returns flowResponse

        viewModel.fetchHoldingResponse()

        repository.fetchHoldingResponse()
        coVerify { repository.fetchHoldingResponse() }
        advanceUntilIdle()

        val responseList = mutableListOf<UserHolding>()
        flowResponse.collect { resultState ->
            responseList.addAll(
                (resultState as ResultState.Success).data.data?.userHolding ?: emptyList()
            )
        }
        assertEquals(responseList, viewModel.uiState.value.holdingList.items)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test fetch holding list then returns error`() = runTest (coroutinesTestRule.testDispatcher) {

        val flowResponse = flow<ResultState<HoldingResponseModel>> {
            ResultState.Error(Throwable("No Data"))
        }
        coEvery { repository.fetchHoldingResponse() } returns flowResponse

        viewModel.fetchHoldingResponse()

        repository.fetchHoldingResponse()
        coVerify { repository.fetchHoldingResponse() }
        advanceUntilIdle()

        var error = Throwable()
        flowResponse.collect { resultState ->
            error = (resultState as ResultState.Error).exception
        }
        assertEquals(error.message, viewModel.uiState.value.error?.message)
    }

    @After
    fun tearDown() {
        unmockkObject(repository, viewModel)
    }

    private fun parseHoldingResponseJson(jsonString: String): HoldingResponseModel {
        return Gson().fromJson(jsonString, HoldingResponseModel::class.java)
    }

    companion object {
        val JSON_RESPONSE = """{
          "data": {
            "userHolding": [
              {
                "symbol": "MAHABANK",
                "quantity": 990,
                "ltp": 38.05,
                "avgPrice": 35,
                "close": 40
              },
              {
                "symbol": "ICICI",
                "quantity": 100,
                "ltp": 118.25,
                "avgPrice": 110,
                "close": 105
              },
              {
                "symbol": "SBI",
                "quantity": 150,
                "ltp": 550.05,
                "avgPrice": 501,
                "close": 590
              },
              {
                "symbol": "TATA STEEL",
                "quantity": 200,
                "ltp": 137,
                "avgPrice": 110.65,
                "close": 100.05
              },
              {
                "symbol": "INFOSYS",
                "quantity": 121,
                "ltp": 1305,
                "avgPrice": 1245.45,
                "close": 1103.85
              },
              {
                "symbol": "AIRTEL",
                "quantity": 415,
                "ltp": 340.75,
                "avgPrice": 370.1,
                "close": 290
              },
              {
                "symbol": "UCO BANK",
                "quantity": 2000,
                "ltp": 18.05,
                "avgPrice": 28.15,
                "close": 22.25
              },
              {
                "symbol": "NHPC",
                "quantity": 900,
                "ltp": 88.05,
                "avgPrice": 80.75,
                "close": 70.65
              },
              {
                "symbol": "SJVN",
                "quantity": 400,
                "ltp": 113.05,
                "avgPrice": 105,
                "close": 110
              },
              {
                "symbol": "PNB BANK",
                "quantity": 100,
                "ltp": 132.05,
                "avgPrice": 100,
                "close": 145.55
              },
              {
                "symbol": "RELIANCE",
                "quantity": 50,
                "ltp": 2500,
                "avgPrice": 2450,
                "close": 2600
              },
              {
                "symbol": "HDFC",
                "quantity": 75,
                "ltp": 1800.25,
                "avgPrice": 1750,
                "close": 1700
              },
              {
                "symbol": "MARUTI",
                "quantity": 30,
                "ltp": 7000,
                "avgPrice": 6800,
                "close": 7200
              },
              {
                "symbol": "TCS",
                "quantity": 150,
                "ltp": 3500,
                "avgPrice": 3400,
                "close": 3300
              },
              {
                "symbol": "HCL",
                "quantity": 200,
                "ltp": 1000,
                "avgPrice": 980,
                "close": 1050
              },
              {
                "symbol": "WIPRO",
                "quantity": 300,
                "ltp": 500,
                "avgPrice": 480,
                "close": 520
              },
              {
                "symbol": "BPCL",
                "quantity": 80,
                "ltp": 400,
                "avgPrice": 380,
                "close": 420
              },
              {
                "symbol": "HPCL",
                "quantity": 60,
                "ltp": 300,
                "avgPrice": 290,
                "close": 320
              },
              {
                "symbol": "ONGC",
                "quantity": 120,
                "ltp": 150,
                "avgPrice": 140,
                "close": 160
              },
              {
                "symbol": "IOC",
                "quantity": 200,
                "ltp": 120,
                "avgPrice": 110,
                "close": 130
              },
              {
                "symbol": "HINDALCO",
                "quantity": 150,
                "ltp": 400,
                "avgPrice": 380,
                "close": 420
              },
              {
                "symbol": "ADANI PORTS",
                "quantity": 500,
                "ltp": 800,
                "avgPrice": 780,
                "close": 820
              },
              {
                "symbol": "CIPLA",
                "quantity": 100,
                "ltp": 900,
                "avgPrice": 880,
                "close": 920
              },
              {
                "symbol": "JSW STEEL",
                "quantity": 250,
                "ltp": 600,
                "avgPrice": 580,
                "close": 620
              },
              {
                "symbol": "AXIS BANK",
                "quantity": 300,
                "ltp": 700,
                "avgPrice": 680,
                "close": 720
              }
            ]
          }
        }"""
    }
}