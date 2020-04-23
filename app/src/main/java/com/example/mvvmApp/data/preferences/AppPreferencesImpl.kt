package com.example.mvvmApp.data.preferences

import androidx.annotation.VisibleForTesting
import androidx.preference.PreferenceManager
import com.example.mvvmApp.MyApplication

class AppPreferencesImpl private constructor(
    app: MyApplication
) : AppPreferences {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)

    private val keyIsFirstRun : String ="IS_FIRST_RUN"
    override var initIsFirstRun : Boolean = false

    override var isFirstRun: Boolean
        get() {
            return sharedPreferences.getBoolean(keyIsFirstRun, initIsFirstRun)
        }
        set(value) {
            sharedPreferences.edit().putBoolean(keyIsFirstRun, value).commit()
        }

    private val keyDisableSetting : String ="DISABLE_SETTING"
    override var initDisableSetting: Boolean = false
    override var disableSetting: Boolean
        get() {
            return sharedPreferences.getBoolean(keyDisableSetting, initDisableSetting)
        }
        set(value) {
            sharedPreferences.edit().putBoolean(keyDisableSetting, value).commit()
        }


    companion object {

        @Volatile
        private var INSTANCE: AppPreferencesImpl? = null

        fun getInstance(app: MyApplication) =
            INSTANCE ?: synchronized(AppPreferencesImpl::class.java) {
                INSTANCE ?: AppPreferencesImpl(app).also {
                    INSTANCE = it
                }
            }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}