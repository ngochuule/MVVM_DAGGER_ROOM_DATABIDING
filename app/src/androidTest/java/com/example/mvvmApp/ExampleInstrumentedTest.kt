package com.example.mvvmApp

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.mvvmApp.data.datasource.api.APIDataSource
import com.example.mvvmApp.data.datasource.appinfo.AppInfo
import com.example.mvvmApp.data.datasource.local.LocalDataSource
import com.example.mvvmApp.data.preferences.AppPreferences
import com.example.mvvmApp.data.repository.AppRepositoryImpl
import com.example.mvvmApp.di.AppModule
import com.example.mvvmApp.di.DaggerAppComponent
import com.example.mvvmApp.ui.mainpage.HomeActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.cglib.core.Local

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    var activityTestRule : ActivityTestRule<HomeActivity> =
        ActivityTestRule(HomeActivity::class.java, false, false)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val app = appContext.applicationContext as MyApplication
//        val localDataSource = mock(LocalDataSource::class.java)
        val localDataSource = object : LocalDataSource by mock(LocalDataSource::class.java){
            override val appPreferences = object : AppPreferences by mock(AppPreferences::class.java){
                override var isFirstRun : Boolean = false
                override var disableSetting : Boolean = false
            }
            override val appInfo = object  : AppInfo by mock(AppInfo::class.java){}
        }
        val apiDataSource = mock(APIDataSource::class.java)
        val appComponent = DaggerAppComponent.builder().application(app).appModule(
            object : AppModule(){
                override fun provideRepository(app_: MyApplication) =
                           AppRepositoryImpl.getInstance(app, localDataSource, apiDataSource )
            }).build()
        app.appComponent = appComponent
        activityTestRule.launchActivity(Intent(appContext, HomeActivity::class.java))
        assertEquals("com.example.mvvmApp", appContext.packageName)
    }
}
