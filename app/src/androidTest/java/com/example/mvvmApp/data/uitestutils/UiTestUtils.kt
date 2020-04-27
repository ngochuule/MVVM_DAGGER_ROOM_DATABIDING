package com.example.mvvmApp.data.uitestutils

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObjectNotFoundException
import androidx.test.uiautomator.UiSelector
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.rules.TestWatcher
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class UiTestUtils {

    private lateinit var mActivity: Activity

    private var screenShotCounter: Int=0
    private lateinit var screenShotDir: String
    private val screenShotDirFormat: String ="yyyyMMdd_HHmmss"
    private lateinit var filePrefix : String
    private var paramRemoveSuccessScreenShots : Boolean = true
    private val msgTAG ="[MSG] << UI TEST >>"
    private val charPool: List<Char> =('a'..'z')+('A'..'Z')+('0'..'9')

    enum class SLEEPTYPE{
        LONG, NORMAL, SHORT, VERYSHORT
    }

    init{
        updateFilePrefix()
        log_e("[Begin Test] filePrefix: $filePrefix")
    }

    fun setActivity(activity: Activity){
        this.mActivity = activity
    }

    fun log_e(message: String) {
        Log.e(msgTAG, message)
    }

    fun log_d(message: String) {
        Log.d(msgTAG, message)
    }

    private fun setParamRemoveSuccessScreenshots(valToSet: Boolean){
        this.paramRemoveSuccessScreenShots = valToSet
    }

    private fun updateFilePrefix() {
        this.filePrefix = randomString(10)
    }

    private fun randomString(i: Int): String {
        return (1..i)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString { "" }
    }

    fun prepareScreenShot(removeScreenShot: Boolean = true){
        this.setParamRemoveSuccessScreenshots(removeScreenShot)
        val packageName = mActivity.packageName
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern(this.screenShotDirFormat)
        val formatted = current.format(formatter)
        val sdcard = mActivity.externalCacheDir
//        val sdcard = Environment.getDataDirectory()
//        val path = Environment.DIRECTORY_DCIM.toString()
//        val sdcard = File("$path")
        this.screenShotDir ="$sdcard/uitest/$formatted-$packageName-${randomString(10)}"
        val createFile = File(this.screenShotDir).mkdirs()
        log_e("saveDirectory = ${this.screenShotDir}")
        log_e("saveDirectory = $createFile")
    }

    fun captureScreenShot(path: File){
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).also {
            it.takeScreenshot(path)
        }
    }

    fun screenShot(type: String ="", message: String ="(NO MESSAGE)"): String{
        screenShotCounter +=1
        var picNumber = String.format("%06d", screenShotCounter)
        var path = "${this.screenShotDir}/Screenshot-${this.filePrefix}-$picNumber-$type.png"
        log_e("fileName: $path")
        var mPath = File(path)
        captureScreenShot(mPath)
        val sMessage = "📸 🖌 MESSAGE=[$message] PATH=[$mPath]"
        log_e(sMessage)
        while(!mPath.exists()) {
            this.sleep(SLEEPTYPE.SHORT)
        }
        return mPath.toString()
    }

    fun sleep(type: SLEEPTYPE){
        var time = 1000
        time = when(type){
            SLEEPTYPE.LONG -> 2500
            SLEEPTYPE.NORMAL -> 1000
            SLEEPTYPE.SHORT -> 800
            SLEEPTYPE.VERYSHORT -> 300
        }
        Thread.sleep(time.toLong())
        ViewActions.closeSoftKeyboard()
    }

    fun removeSuccessScreenShots(){
        if(!this.paramRemoveSuccessScreenShots){
            return
        }
        File("$screenShotDir/").walkTopDown().forEach {
            if(it.name.contains("${this.filePrefix}-.*.png$".toRegex())) {
                it.delete()
                while (it.exists()) {
                    this.sleep(SLEEPTYPE.SHORT)
                }
            }
        }
        File("$screenShotDir/").delete()
    }

    private fun findObjectPermission(){
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).also {
            var allowPermissions = it.findObject(
                UiSelector().clickable(true).checkable(false).index(1)
            )
            if(allowPermissions.exists()){
                try {
                    allowPermissions.click()
                }catch (e: UiObjectNotFoundException){
                    log_e("[Allow Button Does Not Found]")
                }
            }
        }
    }

    fun allowPermissionsIfNeeded(){
        if(Build.VERSION.SDK_INT >= 23){
            this.findObjectPermission()
        }
    }

    fun cAP(parentMatcher: Matcher<View>, position: Int): Matcher<View>{
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    // get and return text
    fun getText(matcher: Matcher<View>): String {
        var stringHolder = emptyArray<String>()
        Espresso.onView(matcher).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "getting text from a TextView"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView //Save, because of check in getConstraints()
                stringHolder = arrayOf(tv.text.toString())
            }
        })
        return stringHolder[0]
    }
}

class ScreenshotTakingRule(mUTs: UiTestUtils) : TestWatcher() {
    private var mUTs = mUTs

    override fun failed(e: Throwable?, description: org.junit.runner.Description?) {
        super.failed(e, description)
        mUTs.log_e(e.toString())
        val path = mUTs.screenShot("FAIL-$description")
        mUTs.log_e(">>> !!! TEST FAILED !!! <<< ScreenShot Taken method=[$description] filename=[$path]")
    }
}