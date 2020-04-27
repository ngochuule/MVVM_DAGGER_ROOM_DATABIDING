package com.example.mvvmApp.data.ui.mainpage

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.example.mvvmApp.R
import com.example.mvvmApp.data.uitestutils.ScreenshotTakingRule
import com.example.mvvmApp.data.uitestutils.UiTestUtils
import com.example.mvvmApp.ui.mainpage.HomeActivity
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 21)
@LargeTest
class HomeActivityInstrumentedTest {
//    private val _packageName ="com.example.mvvmApp.data.ui.mainpage"
    private val _packageName ="com.example.mvvmApp"
    private val mUTs: UiTestUtils = UiTestUtils()

    @Rule
    @JvmField
    val cGrantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    @Rule
    @JvmField
    val mActivityTestRule: ActivityTestRule<HomeActivity> = ActivityTestRule(HomeActivity::class.java)

    @Rule
    @JvmField
    val screenshotRule = ScreenshotTakingRule(this.mUTs)

    @Before
    fun setup(){
        this.mUTs.setActivity(mActivityTestRule.activity)
    }

    @After
    fun teardown(){

    }

    @Test
    fun useAppContext(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals(_packageName, appContext.packageName)
        this.mUTs.prepareScreenShot()
        this.mUTs.sleep(UiTestUtils.SLEEPTYPE.SHORT)
        this.mUTs.removeSuccessScreenShots()
    }

    @Test
    fun checkNextPageClick(){
        this.mUTs.prepareScreenShot()
        mUTs.screenShot("", "NextPage Click!")
        val nextButton = withId(R.id.button2)
        this.mUTs.sleep(UiTestUtils.SLEEPTYPE.SHORT)
        onView(nextButton).perform(click())
        mUTs.allowPermissionsIfNeeded()
        this.mUTs.sleep(UiTestUtils.SLEEPTYPE.SHORT)
        mUTs.screenShot("", "AFTER >>> NextPage Click")
        this.mUTs.removeSuccessScreenShots()
    }
}