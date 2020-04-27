package com.example.mvvmApp.data.preferences

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.mvvmApp.MyApplication
import com.nhaarman.mockitokotlin2.*
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(androidx.preference.PreferenceManager::class, MyApplication::class)
class AppPreferencesImplTest {
    lateinit var application: MyApplication
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var appPreferences: AppPreferences

    @Before
    fun setUp() {
        PowerMockito.mockStatic(PreferenceManager::class.java)

        application = mock<MyApplication>()
        sharedPreferences = mock<SharedPreferences>()
        editor = mock<SharedPreferences.Editor>()
        whenever(sharedPreferences.edit()).doReturn(editor)
        whenever(editor.putBoolean(any(), any())).doReturn(editor)
        whenever(editor.putString(any(), any())).doReturn(editor)
        whenever(editor.putInt(any(), any())).doReturn(editor)
        whenever(editor.putLong(any(), any())).doReturn(editor)
        whenever(PreferenceManager.getDefaultSharedPreferences(application)).doReturn(sharedPreferences)

        appPreferences = AppPreferencesImpl.getInstance(application)
    }

    @After
    fun tearDown() {
        AppPreferencesImpl.destroyInstance()
    }

    @Test
    fun getInitialValues() {
        assertNull(appPreferences.initDisableSetting)
        assertNull(appPreferences.initIsFirstRun)
    }

    @Test
    fun setApplicationIsFirstRunDefault(){
        appPreferences.isFirstRun = false

        verify(editor, times(1)).putBoolean(any(), eq(false))
        verify(editor, times(1)).commit()
    }

    @Test
    fun setApplicationIsFirstRun(){
        appPreferences.isFirstRun = true

        verify(editor, times(1)).putBoolean(any(), eq(true))
        verify(editor, times(1)).commit()
    }

    @Test
    fun setDisplaySettingPage(){
        appPreferences.disableSetting = false

        verify(editor, times(1)).putBoolean(any(), eq(false))
        verify(editor, times(1)).commit()
    }

    @Test
    fun setEnableDisplaySettingPage(){
        appPreferences.disableSetting = true

        verify(editor, times(1)).putBoolean(any(), eq(true))
        verify(editor, times(1)).commit()
    }
}