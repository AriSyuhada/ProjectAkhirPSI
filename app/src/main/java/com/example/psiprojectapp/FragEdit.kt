package com.example.psiprojectapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.frag_edit.*
import java.text.SimpleDateFormat
import java.util.*

class FragEdit() : Fragment(R.layout.frag_edit) {

    private lateinit var settings : ThemeSettings

    private lateinit var tvlabelDesc : TextView
    private lateinit var tvinputDesc : TextInputEditText
    private lateinit var tvlabelTime : TextView
    private lateinit var containerStartTime : TextInputLayout
    private lateinit var buttonStartTime : ImageButton
    private lateinit var tvinputStartTime : TextInputEditText
    private lateinit var imageviewDash : ImageView
    private lateinit var containerEndTime : TextInputLayout
    private lateinit var buttonEndTime : ImageButton
    private lateinit var tvinputEndTime : TextInputEditText
    private lateinit var buttonSave : Button

    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.frag_edit, container, false)

        settings = activity?.getApplication() as ThemeSettings
        loadSharedPreferences()

        initWidgets(view)
        updateView()

//        sharedPreferences = this.activity.getSharedPreferences("input_data", Context.MODE_PRIVATE)
        val tvDay = getActivity()?.findViewById<TextView>(R.id.tv_day)

        buttonStartTime.setOnClickListener() {
            timeDialog(tvinputStartTime)
        }

        tvinputStartTime.setOnClickListener() {
            timeDialog(tvinputStartTime)
        }

        buttonEndTime.setOnClickListener() {
            timeDialog(tvinputEndTime)
        }

        tvinputEndTime.setOnClickListener() {
            timeDialog(tvinputEndTime)
        }

        buttonEndTime.setOnClickListener() {
            val calendar = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                tvinputEndTime.setText(SimpleDateFormat("HH:mm").format(calendar.time))
            }
            val timePickerDialog = TimePickerDialog(this.context, android.R.style.Theme_Holo_Light_Dialog, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
            timePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            timePickerDialog.show()
        }

        var dataId : Int = 0
        var dataDesc : String
        var dataTimeStart : String
        var dataTimeEnd : String

        if (arguments?.getInt("dataId") !== null) {
            dataId = requireArguments().getInt("dataId")
            dataDesc = requireArguments().getString("dataDesc").toString()
            dataTimeStart = requireArguments().getString("dataTimeStart").toString()
            dataTimeEnd = requireArguments().getString("dataTimeEnd").toString()

            tvinputDesc.setText(dataDesc)
            tvinputStartTime.setText(dataTimeStart)
            tvinputEndTime.setText(dataTimeEnd)
        }

        buttonSave.setOnClickListener() {
            val day = tvDay?.text.toString()
            val desc = tvinputDesc.text.toString()
            val startTime = tvinputStartTime.text.toString()
            val endTime = tvinputEndTime.text.toString()

            var enumDay : Int = 1
            when (day) {
                "Senin" -> enumDay = 1
                "Selasa" -> enumDay = 2
                "Rabu" -> enumDay = 3
                "Kamis" -> enumDay = 4
                "Jumat" -> enumDay = 5
                "Sabtu" -> enumDay = 6
                "Minggu" -> enumDay = 7
            }

            if (desc == "" || startTime == "" || endTime == "") {
                Toast.makeText(this.context, "Please fill the form", Toast.LENGTH_SHORT).show()
            }
            else {
                val scheduleActivity = activity as ScheduleActivity?
                val schedule : Schedule
                if (dataId == 0) {
                    schedule = Schedule(day = day, desc = desc, timeStart = startTime, timeEnd = endTime)
                    scheduleActivity?.getVarScheduleViewModel()?.addData(schedule)
                } else {
                    schedule = Schedule(id = dataId, day = day, desc = desc, timeStart = startTime, timeEnd = endTime)
                    scheduleActivity?.getVarScheduleViewModel()?.updateData(schedule)
                }

                Toast.makeText(this.context, "New schedule successfully added", Toast.LENGTH_SHORT).show()
                tvinputDesc.setText("")
                tvinputStartTime.setText("")
                tvinputEndTime.setText("")
                getActivity()?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
                getActivity()?.findViewById<LinearLayoutCompat>(R.id.layout_edit_menu)?.setVisibility(View.GONE)
            }

        }

        return view
    }

    private fun loadSharedPreferences() {
        val sharedPreferences = activity?.getSharedPreferences(ThemeSettings.PREFERENCES,
            AppCompatActivity.MODE_PRIVATE
        )
        val theme = sharedPreferences?.getString(ThemeSettings.CUSTOM_THEME, ThemeSettings.LIGHT_THEME)
        settings.customTheme = theme
    }

    private fun initWidgets(view : View) {
        tvlabelDesc = view.findViewById(R.id.tv_label_desc)
        tvinputDesc = view.findViewById(R.id.tvinput_description)
        tvlabelTime = view.findViewById(R.id.tv_label_time)
        containerStartTime = view.findViewById(R.id.container_tvinput_start_time)
        buttonStartTime = view.findViewById(R.id.button_start_time)
        tvinputStartTime = view.findViewById(R.id.tvinput_start_time)
        imageviewDash = view.findViewById(R.id.imageview_dash)
        containerEndTime = view.findViewById(R.id.container_tvinput_end_time)
        buttonEndTime = view.findViewById(R.id.button_end_time)
        tvinputEndTime = view.findViewById(R.id.tvinput_end_time)
        buttonSave = view.findViewById(R.id.button_save)
    }

    private fun updateView() {
        if (settings.customTheme.equals(ThemeSettings.DARK_THEME)){
            tvlabelDesc.setTextColor(resources.getColor(R.color.darkColorOnPrimary))
            tvinputDesc.setTextColor(resources.getColor(R.color.darkColorOnPrimary))
            tvinputDesc.setHintTextColor(resources.getColor(R.color.darkColorOnPrimaryHint))
            tvlabelTime.setTextColor(resources.getColor(R.color.darkColorOnPrimary))
            containerStartTime.defaultHintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this.requireContext(), R.color.darkColorOnPrimaryHint))
            containerStartTime.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this.requireContext(), R.color.darkColorSecondary))
            containerStartTime.boxStrokeColor = resources.getColor(R.color.darkColorSecondary)
            buttonStartTime.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this.requireContext(), R.color.darkColorOnPrimary))
            tvinputStartTime.setTextColor(resources.getColor(R.color.darkColorOnPrimary))
            tvinputStartTime.setHintTextColor(resources.getColor(R.color.darkColorOnPrimaryHint))
            imageviewDash.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this.requireContext(), R.color.darkColorOnPrimary))
            containerEndTime.defaultHintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this.requireContext(), R.color.darkColorOnPrimaryHint))
            containerEndTime.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this.requireContext(), R.color.darkColorSecondary))
            containerEndTime.boxStrokeColor = resources.getColor(R.color.darkColorSecondary)
            buttonEndTime.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this.requireContext(), R.color.darkColorOnPrimary))
            tvinputEndTime.setTextColor(resources.getColor(R.color.darkColorOnPrimary))
            tvinputEndTime.setHintTextColor(resources.getColor(R.color.darkColorOnPrimaryHint))
            buttonSave.setBackgroundColor(resources.getColor(R.color.darkColorSecondary))
            buttonSave.setTextColor(resources.getColor(R.color.darkColorOnSecondary))
        } else {
            tvlabelDesc.setTextColor(resources.getColor(R.color.lightColorOnPrimary))
            tvinputDesc.setTextColor(resources.getColor(R.color.lightColorOnPrimary))
            tvinputDesc.setHintTextColor(resources.getColor(R.color.lightColorOnPrimaryHint))
            tvlabelTime.setTextColor(resources.getColor(R.color.lightColorOnPrimary))
            containerStartTime.defaultHintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this.requireContext(), R.color.lightColorOnPrimaryHint))
            containerStartTime.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this.requireContext(), R.color.lightColorSecondary))
            containerStartTime.boxStrokeColor = resources.getColor(R.color.lightColorSecondary)
            buttonStartTime.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this.requireContext(), R.color.lightColorOnPrimary))
            tvinputStartTime.setTextColor(resources.getColor(R.color.lightColorOnPrimary))
            tvinputStartTime.setHintTextColor(resources.getColor(R.color.lightColorOnPrimaryHint))
            imageviewDash.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this.requireContext(), R.color.lightColorOnPrimary))
            containerEndTime.defaultHintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this.requireContext(), R.color.lightColorOnPrimaryHint))
            containerEndTime.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this.requireContext(), R.color.lightColorSecondary))
            containerEndTime.boxStrokeColor = resources.getColor(R.color.lightColorSecondary)
            buttonEndTime.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this.requireContext(), R.color.lightColorOnPrimary))
            tvinputEndTime.setTextColor(resources.getColor(R.color.lightColorOnPrimary))
            tvinputEndTime.setHintTextColor(resources.getColor(R.color.lightColorOnPrimaryHint))
            buttonSave.setBackgroundColor(resources.getColor(R.color.lightColorSecondary))
            buttonSave.setTextColor(resources.getColor(R.color.lightColorOnSecondary))
        }
    }

    private fun timeDialog(editText: TextInputEditText) {
        val calendar = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            editText.setText(SimpleDateFormat("HH:mm").format(calendar.time))
        }
        val timePickerDialog = TimePickerDialog(this.context, android.R.style.Theme_Holo_Light_Dialog, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
        timePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        timePickerDialog.show()
    }

}