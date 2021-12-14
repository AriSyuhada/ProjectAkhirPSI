package com.example.psiprojectapp

import android.app.Application

class ThemeSettings : Application() {
    var customTheme: String? = null

    companion object {
        const val PREFERENCES = "preferences"
        const val CUSTOM_THEME = "customTheme"
        const val LIGHT_THEME = "lightTheme"
        const val DARK_THEME = "darkTheme"
    }
}