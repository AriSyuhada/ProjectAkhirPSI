package com.example.psiprojectapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.switchmaterial.SwitchMaterial

import android.widget.CompoundButton
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private lateinit var parentView : View
    private lateinit var linearLayoutCompat : LinearLayoutCompat
    private lateinit var buttonMenu : Button
    private lateinit var imageviewLogo : ImageView
    private lateinit var switchTheme : SwitchMaterial
    private lateinit var tvTitle : TextView
    private lateinit var tvTheme : TextView
    private lateinit var settings : ThemeSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragDays = FragDays()
        val buttonMenu = findViewById<Button>(R.id.button_menu)

        settings = getApplication() as ThemeSettings
        loadSharedPreferences()

        initWidgets()
        updateView()
        initSwitchListener()

        buttonMenu.setOnClickListener() {
            supportFragmentManager.beginTransaction().apply {
                if (fragDays.isAdded) {
                    remove(fragDays)
                    commit()
                }
                else {
                    add(R.id.scrollView_days, fragDays)
                    commit()
                }
            }
        }

    }

    private fun initSwitchListener() {
        switchTheme.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, checked ->
            if (checked) settings.customTheme = ThemeSettings.DARK_THEME else settings.customTheme = ThemeSettings.LIGHT_THEME
            val editor = getSharedPreferences(ThemeSettings.PREFERENCES, MODE_PRIVATE).edit()
            editor.putString(ThemeSettings.CUSTOM_THEME, settings.customTheme)
            editor.apply()
            updateView()
        })
    }

    private fun updateView() {
        if (settings.customTheme.equals(ThemeSettings.DARK_THEME)){
            tvTitle.setTextColor(resources.getColor(R.color.darkColorOnPrimary))
            tvTheme.setTextColor(resources.getColor(R.color.darkColorOnPrimary))
            tvTheme.setText("Dark")
            linearLayoutCompat.setBackground(resources.getDrawable(R.drawable.bg_menu_dark))
            buttonMenu.setBackgroundTintList(ContextCompat.getColorStateList(applicationContext, R.color.darkColorPrimary))
            imageviewLogo.setImageResource(R.drawable.logo_light)
            parentView.setBackgroundColor(resources.getColor(R.color.darkColorPrimary))
            switchTheme.setChecked(true)
        } else {
            tvTitle.setTextColor(resources.getColor(R.color.lightColorOnPrimary))
            tvTheme.setTextColor(resources.getColor(R.color.lightColorOnPrimary))
            tvTheme.setText("Light")
            linearLayoutCompat.setBackground(resources.getDrawable(R.drawable.bg_menu_light))
            buttonMenu.setBackgroundTintList(ContextCompat.getColorStateList(applicationContext, R.color.lightColorPrimary))
            imageviewLogo.setImageResource(R.drawable.logo_dark)
            parentView.setBackgroundColor(resources.getColor(R.color.lightColorPrimary))
            switchTheme.setChecked(false)
        }
    }

    private fun loadSharedPreferences() {
        val sharedPreferences = getSharedPreferences(ThemeSettings.PREFERENCES, MODE_PRIVATE)
        val theme = sharedPreferences.getString(ThemeSettings.CUSTOM_THEME, ThemeSettings.LIGHT_THEME)
        settings.customTheme = theme
    }

    private fun initWidgets() {
        this.parentView = findViewById(R.id.parent_view)
        this.linearLayoutCompat = findViewById(R.id.linearLayoutCompat)
        this.buttonMenu = findViewById(R.id.button_menu)
        this.imageviewLogo = findViewById(R.id.imageview_logo)
        this.switchTheme = findViewById(R.id.switch_theme)
        this.tvTitle = findViewById(R.id.tv_title)
        this.tvTheme = findViewById(R.id.tv_theme)
    }
}