package com.example.psiprojectapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class FragDays : Fragment(R.layout.frag_days) {

    private lateinit var settings : ThemeSettings

    private lateinit var buttonSenin : Button
    private lateinit var buttonSelasa : Button
    private lateinit var buttonRabu : Button
    private lateinit var buttonKamis : Button
    private lateinit var buttonJumat : Button
    private lateinit var buttonSabtu : Button
    private lateinit var buttonMinggu : Button

    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.frag_days, container, false)

        settings = activity?.getApplication() as ThemeSettings
        loadSharedPreferences()

        initWidgets(view)
        updateView()

        buttonSenin.setOnClickListener() {
            val intent = Intent(activity, ScheduleActivity::class.java)
            intent.putExtra("DAY_OF_SCHEDULE", getDay(buttonSenin))
            startActivity(intent)
        }

        buttonSelasa.setOnClickListener() {
            val intent = Intent(activity, ScheduleActivity::class.java)
            intent.putExtra("DAY_OF_SCHEDULE", getDay(buttonSelasa))
            startActivity(intent)
        }

        buttonRabu.setOnClickListener() {
            val intent = Intent(activity, ScheduleActivity::class.java)
            intent.putExtra("DAY_OF_SCHEDULE", getDay(buttonRabu))
            startActivity(intent)
        }

        buttonKamis.setOnClickListener() {
            val intent = Intent(activity, ScheduleActivity::class.java)
            intent.putExtra("DAY_OF_SCHEDULE", getDay(buttonKamis))
            startActivity(intent)
        }

        buttonJumat.setOnClickListener() {
            val intent = Intent(activity, ScheduleActivity::class.java)
            intent.putExtra("DAY_OF_SCHEDULE", getDay(buttonJumat))
            startActivity(intent)
        }

        buttonSabtu.setOnClickListener() {
            val intent = Intent(activity, ScheduleActivity::class.java)
            intent.putExtra("DAY_OF_SCHEDULE", getDay(buttonSabtu))
            startActivity(intent)
        }

        buttonMinggu.setOnClickListener() {
            val intent = Intent(activity, ScheduleActivity::class.java)
            intent.putExtra("DAY_OF_SCHEDULE", getDay(buttonMinggu))
            startActivity(intent)
        }

        return view
    }

    fun getDay(button : Button) : String {
        return button.text.toString()
    }

    private fun loadSharedPreferences() {
        val sharedPreferences = activity?.getSharedPreferences(ThemeSettings.PREFERENCES,
            AppCompatActivity.MODE_PRIVATE
        )
        val theme = sharedPreferences?.getString(ThemeSettings.CUSTOM_THEME, ThemeSettings.LIGHT_THEME)
        settings.customTheme = theme
    }

    private fun initWidgets(view : View) {
        this.buttonSenin = view.findViewById(R.id.button_senin)
        this.buttonSelasa = view.findViewById(R.id.button_selasa)
        this.buttonRabu = view.findViewById(R.id.button_rabu)
        this.buttonKamis = view.findViewById(R.id.button_kamis)
        this.buttonJumat = view.findViewById(R.id.button_jumat)
        this.buttonSabtu = view.findViewById(R.id.button_sabtu)
        this.buttonMinggu = view.findViewById(R.id.button_minggu)
    }

    private fun updateView() {
        if (settings.customTheme.equals(ThemeSettings.DARK_THEME)){
            buttonSenin.setBackgroundColor(resources.getColor(R.color.darkColorSecondary))
            buttonSenin.setTextColor(resources.getColor(R.color.darkColorOnSecondary))
            buttonSelasa.setBackgroundColor(resources.getColor(R.color.darkColorSecondary))
            buttonSelasa.setTextColor(resources.getColor(R.color.darkColorOnSecondary))
            buttonRabu.setBackgroundColor(resources.getColor(R.color.darkColorSecondary))
            buttonRabu.setTextColor(resources.getColor(R.color.darkColorOnSecondary))
            buttonKamis.setBackgroundColor(resources.getColor(R.color.darkColorSecondary))
            buttonKamis.setTextColor(resources.getColor(R.color.darkColorOnSecondary))
            buttonJumat.setBackgroundColor(resources.getColor(R.color.darkColorSecondary))
            buttonJumat.setTextColor(resources.getColor(R.color.darkColorOnSecondary))
            buttonSabtu.setBackgroundColor(resources.getColor(R.color.darkColorSecondary))
            buttonSabtu.setTextColor(resources.getColor(R.color.darkColorOnSecondary))
            buttonMinggu.setBackgroundColor(resources.getColor(R.color.darkColorSecondary))
            buttonMinggu.setTextColor(resources.getColor(R.color.darkColorOnSecondary))
        } else {
            buttonSenin.setBackgroundColor(resources.getColor(R.color.lightColorSecondary))
            buttonSenin.setTextColor(resources.getColor(R.color.lightColorOnSecondary))
            buttonSelasa.setBackgroundColor(resources.getColor(R.color.lightColorSecondary))
            buttonSelasa.setTextColor(resources.getColor(R.color.lightColorOnSecondary))
            buttonRabu.setBackgroundColor(resources.getColor(R.color.lightColorSecondary))
            buttonRabu.setTextColor(resources.getColor(R.color.lightColorOnSecondary))
            buttonKamis.setBackgroundColor(resources.getColor(R.color.lightColorSecondary))
            buttonKamis.setTextColor(resources.getColor(R.color.lightColorOnSecondary))
            buttonJumat.setBackgroundColor(resources.getColor(R.color.lightColorSecondary))
            buttonJumat.setTextColor(resources.getColor(R.color.lightColorOnSecondary))
            buttonSabtu.setBackgroundColor(resources.getColor(R.color.lightColorSecondary))
            buttonSabtu.setTextColor(resources.getColor(R.color.lightColorOnSecondary))
            buttonMinggu.setBackgroundColor(resources.getColor(R.color.lightColorSecondary))
            buttonMinggu.setTextColor(resources.getColor(R.color.lightColorOnSecondary))
        }
    }

}