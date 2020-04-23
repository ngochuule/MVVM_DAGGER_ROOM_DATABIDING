package com.example.mvvmApp.data.preferences

interface AppPreferences {

    var isFirstRun: Boolean
    var initIsFirstRun: Boolean

    var disableSetting: Boolean
    var initDisableSetting: Boolean
}