package com.example.psiprojectapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SplashScreen  : AppCompatActivity()  {

    lateinit var handler : Handler

    private lateinit var parentView : View
    private lateinit var imageviewLogo : ImageView
    private lateinit var tvTitle : TextView
    private lateinit var settings : ThemeSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

        settings = getApplication() as ThemeSettings
        loadSharedPreferences()

        initWidgets()
        updateView()

        handler = Handler()
        handler.postDelayed({

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }, 3000)

    }

    private fun loadSharedPreferences() {
        val sharedPreferences = getSharedPreferences(ThemeSettings.PREFERENCES, MODE_PRIVATE)
        val theme = sharedPreferences.getString(ThemeSettings.CUSTOM_THEME, ThemeSettings.LIGHT_THEME)
        settings.customTheme = theme
    }

    private fun initWidgets() {
        this.parentView = findViewById(R.id.parent_view)
        this.imageviewLogo = findViewById(R.id.imageView)
        this.tvTitle = findViewById(R.id.textView)
    }

    private fun updateView() {
        if (settings.customTheme.equals(ThemeSettings.DARK_THEME)){
            tvTitle.setTextColor(resources.getColor(R.color.darkColorOnPrimary))
            imageviewLogo.setImageResource(R.drawable.logo_light)
            parentView.setBackgroundColor(resources.getColor(R.color.darkColorPrimary))
        } else {
            tvTitle.setTextColor(resources.getColor(R.color.lightColorOnPrimary))
            imageviewLogo.setImageResource(R.drawable.logo_dark)
            parentView.setBackgroundColor(resources.getColor(R.color.lightColorPrimary))
        }
    }

}