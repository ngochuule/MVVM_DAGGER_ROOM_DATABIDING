package com.example.mvvmApp

import com.example.mvvmApp.data.datasource.api.APIClient.setConnection
import com.example.mvvmApp.data.model.HomeModel
import com.example.mvvmApp.data.model.SinglePostResponse
import com.example.mvvmApp.unittestutils.UnitTestUtils
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert
import org.junit.Test

internal class MockServerDispatcher{
    val  mockedResponse = arrayOf(
        HomeModel("1","Name 1", true),
        HomeModel("2","Name 2", false),
        HomeModel("3","Name 3", true),
        HomeModel("4","Name 4", false)
    )
    internal inner class Response200: Dispatcher(){
        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse()
                .setResponseCode(200)
                .setBody(Gson().toJson(mockedResponse))
        }
    }
    internal inner class Response400: Dispatcher(){
        override fun dispatch(request: RecordedRequest?): MockResponse {
            return MockResponse().setResponseCode(400)
        }
    }internal inner class Response500: Dispatcher(){
        override fun dispatch(request: RecordedRequest?): MockResponse {
            return MockResponse().setResponseCode(500)
        }
    }

}

/**
 * [testing documentation] http://d.android.com/tools/testing.
 */
class HomeActivityUnitTest {
    private var mMockTestUtils = UnitTestUtils()

    @Test
    fun sampleUnitDataFetchSuccessTest() {
        mMockTestUtils.mockServerBehaviorSwitcher = {
            MockServerDispatcher().Response200()
        }
//        val expectedResponse = MockServerDispatcher().mockedResponse
//
//        var mMainActivityPresenter = MainActivityPresenter()
//        var mMainActivityViewContract = mock<MainActivityViewContract>()
//
//        mMainActivityPresenter.setView(mMainActivityViewContract)
//
//        mMockTestUtils.prepareRxForTesting()
//
//        mMockTestUtils.startMockServer()
//        var mApiConnection = mMockTestUtils.setupMockServer()
//        setConnection(mApiConnection)
//
//        mMainActivityPresenter.getJsonSampleResponse()
//        argumentCaptor<Array<SinglePostResponse>>().apply {
//            verify(mMainActivityViewContract, times(1)).handleSuccess(capture())
//
//            for (i in 0 until expectedResponse.size) {
//                val expected = expectedResponse[i]
//                val actual = this.firstValue[i]
//                mMockTestUtils.assertDataClass(expected, actual)
//            }
//
//        }
        mMockTestUtils.shutdownMockServer()
    }

    @Test
    fun sampleUnit400BadRequestTest() {
        mMockTestUtils.mockServerBehaviorSwitcher = {
            MockServerDispatcher().Response400()
        }
//
//        var mMainActivityPresenter = MainActivityPresenter()
//        var mMainActivityViewContract = mock<MainActivityViewContract>()
//
//        mMainActivityPresenter.setView(mMainActivityViewContract)
//
//        mMockTestUtils.prepareRxForTesting()
//
//        mMockTestUtils.startMockServer()
//        var mApiConnection = mMockTestUtils.setupMockServer()
//        setConnection(mApiConnection)
//
//        mMainActivityPresenter.getJsonSampleResponse()
//        argumentCaptor<String>().apply {
//            verify(mMainActivityViewContract, times(1)).handleError(capture())
//            Assert.assertEquals("HTTP 400 Client Error", this.firstValue)
//        }
        mMockTestUtils.shutdownMockServer()
    }

    @Test
    fun sampleUnit500ServerErrorTest() {
        mMockTestUtils.mockServerBehaviorSwitcher = {
            MockServerDispatcher().Response500()
        }

//        var mMainActivityPresenter = MainActivityPresenter()
//        var mMainActivityViewContract = mock<MainActivityViewContract>()
//
//        mMainActivityPresenter.setView(mMainActivityViewContract)
//
//        mMockTestUtils.prepareRxForTesting()
//
//        mMockTestUtils.startMockServer()
//        var mApiConnection = mMockTestUtils.setupMockServer()
//        setConnection(mApiConnection)
//
//        mMainActivityPresenter.getJsonSampleResponse()
//        argumentCaptor<String>().apply {
//            verify(mMainActivityViewContract, times(1)).handleError(capture())
//            Assert.assertEquals("HTTP 500 Server Error", this.firstValue)
//        }
        mMockTestUtils.shutdownMockServer()
    }
}