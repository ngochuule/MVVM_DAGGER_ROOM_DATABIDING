package com.example.mvvmApp.data.datasource

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.mvvmApp.MyApplication
import com.example.mvvmApp.data.datasource.appinfo.AppInfoImpl
import com.example.mvvmApp.ui.mainpage.HomeActivity
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppInfoImplTest {
    @get:Rule
    var activityTestRule : ActivityTestRule<HomeActivity> =
        ActivityTestRule(HomeActivity::class.java, false, false)

    @Test
    fun testAppIsAvailable_Normal(){
        // Get context of the application under test
        val appcontext = InstrumentationRegistry.getInstrumentation().targetContext
        val app = appcontext.applicationContext as MyApplication
        val appInfo = AppInfoImpl.getInstance(app)

        assertEquals(true,appInfo.isAvailable("com.android.chrome"))
        assertEquals(true,appInfo.isAvailable("com.android.firefox"))
        assertTrue(appInfo.isAvailable("com.android.contacts"))
        assertTrue(appInfo.isAvailable("com.android.firefox"))
        assertFalse(appInfo.isAvailable("com.android.contacts"))
        assertFalse(appInfo.isAvailable("com.android.firefox"))
    }

    @Test
    fun testAppIsAvailable_Disable(){
        // Get context of the application under test
        val appcontext = InstrumentationRegistry.getInstrumentation().targetContext
        val app = appcontext.applicationContext as MyApplication
        val appInfo = AppInfoImpl.getInstance(app)

        // To test this, disable "YouTube" app.
        if (false) {
            assertEquals(false, appInfo.isAvailable("com.google.android.youtube"))
        }
    }

    @Test
    fun testGetInstalledApplications_ThisApp(){
        // Get context of the application under test
        val appcontext = InstrumentationRegistry.getInstrumentation().targetContext
        val app = appcontext.applicationContext as MyApplication
        val appInfo = AppInfoImpl.getInstance(app)

        var count = appInfo.getInstalledApplications()
                        .filter { it.appId == appcontext.packageName }
                        .count()
        assertEquals(0, count)
    }

    @Test
    fun testGetInstalledApplications_All(){
        // Get context of the application under test
        val appcontext = InstrumentationRegistry.getInstrumentation().targetContext
        val app = appcontext.applicationContext as MyApplication
        val appInfo = AppInfoImpl.getInstance(app)

        var count = appInfo.getInstalledApplications()
            .count()
        Log.e("TEST_DATA","count $count")
        assertNotEquals(0, count)
    }

    @Test
    fun testGetInstalledApplication_Disable(){
        // Get context of the application under test
        val appcontext = InstrumentationRegistry.getInstrumentation().targetContext
        val app = appcontext.applicationContext as MyApplication
        val appInfo = AppInfoImpl.getInstance(app)

        // To test this, disable "YouTube" app.
        if (false) {
            val count = appInfo.getInstalledApplications()
                .filter { it.appId == "com.google.android.youtube" }
                .count()
            assertEquals(0, count)
        }
    }

    @Test
    fun testGetApplicationName_Found(){
        // Get context of the application under test
        val appcontext = InstrumentationRegistry.getInstrumentation().targetContext
        val app = appcontext.applicationContext as MyApplication
        val appInfo = AppInfoImpl.getInstance(app)

        val pm = appcontext.packageManager
        "com.android.chrome".also {
            assertEquals(pm.getApplicationLabel(pm.getApplicationInfo(it, 0)), appInfo.getApplicationName(it))
        }
        "com.android.contacts".also {
            assertEquals(pm.getApplicationLabel(pm.getApplicationInfo(it, 0)), appInfo.getApplicationName(it))
        }
    }

    @Test
    fun testGetApplicationName_NotFound(){
        // Get context of the application under test
        val appcontext = InstrumentationRegistry.getInstrumentation().targetContext
        val app = appcontext.applicationContext as MyApplication
        val appInfo = AppInfoImpl.getInstance(app)

        val appName = appInfo.getApplicationName("testApp")
        Log.e("TEST_DATA","appName $appName")
    }
}