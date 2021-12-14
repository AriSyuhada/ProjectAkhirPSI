package com.example.psiprojectapp

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager

import android.app.PendingIntent

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_schedule.*
import java.util.*


class ScheduleActivity : itemOnClickListener, AppCompatActivity() {

    private lateinit var scheduleViewModel : ScheduleViewModel
    private lateinit var layoutEditMenu : LinearLayoutCompat
    private lateinit var buttonMenu : Button
    private lateinit var parentView : View
    private lateinit var buttonBack : ImageButton
    private lateinit var tvTitle : TextView
    private lateinit var tvDay : TextView
    private lateinit var tvEmptyItem : TextView
    private lateinit var buttonAdd : ImageButton
    private lateinit var containerSearch : TextInputLayout
    private lateinit var tvinputSearch : TextInputEditText

    private lateinit var recyclerViewSchedule : RecyclerView
    private lateinit var scheduleAdapter : ScheduleAdapter

    private lateinit var settings : ThemeSettings

    private lateinit var dayString : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        settings = getApplication() as ThemeSettings
        loadSharedPreferences()

        initWidgets()
        updateView()

        buttonBack.setOnClickListener() {
            finish()
        }

        if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras == null) {
                dayString = ""
            }
            else {
                dayString = extras.getString("DAY_OF_SCHEDULE").toString()
            }
        }
        else {
            dayString = savedInstanceState.getSerializable("DAY_OF_SCHEDULE").toString()
        }

        setVarScheduleViewModel(this.applicationContext)
        scheduleAdapter = ScheduleAdapter(scheduleViewModel.getDatabyDay(dayString), this)

        tvDay.text = dayString

        startEditFrag()

        recyclerViewSchedule.layoutManager = LinearLayoutManager(this)

        getLiveDataByDay()

        val observableData = observableData()

        observableData.distinctUntilChanged()
            .subscribe() { words ->
                getLiveDataByWords(words)
            }
    }

    private fun observableData() : Observable<String> {
        return PublishSubject.create { emitter ->
            tvinputSearch.addTextChangedListener {
                emitter.onNext(tvinputSearch.text.toString())
            }
        }
    }

    private fun getLiveDataByDay() {
        scheduleViewModel.getDatabyDay(dayString).observe(this, Observer {
            if (scheduleAdapter.itemCount == 0) {
                recyclerViewSchedule.adapter = scheduleAdapter
                tvEmptyItem.setVisibility(View.VISIBLE)
            }
            else {
                tvEmptyItem.setVisibility(View.GONE)
                Toast.makeText(this, "Schedule has been set", Toast.LENGTH_SHORT).show()
                recyclerViewSchedule.adapter = scheduleAdapter
            }
        })
    }

    private fun getLiveDataByWords(words : String) {
        scheduleViewModel.getDataByWords(dayString, words).observe(this, Observer {
            if (scheduleAdapter.itemCount == 0) {
                recyclerViewSchedule.adapter = scheduleAdapter
                tvEmptyItem.setVisibility(View.VISIBLE)
            }
            else {
                tvEmptyItem.setVisibility(View.GONE)
                Toast.makeText(this, "Schedule has been set", Toast.LENGTH_SHORT).show()
                recyclerViewSchedule.adapter = scheduleAdapter
            }
        })
    }

    private fun loadSharedPreferences() {
        val sharedPreferences = getSharedPreferences(ThemeSettings.PREFERENCES, MODE_PRIVATE)
        val theme = sharedPreferences.getString(ThemeSettings.CUSTOM_THEME, ThemeSettings.LIGHT_THEME)
        settings.customTheme = theme
    }

    private fun initWidgets() {
        this.parentView = findViewById(R.id.parent_view)
        this.layoutEditMenu = findViewById(R.id.layout_edit_menu)
        this.buttonMenu = findViewById(R.id.button_edit_menu)
        this.buttonBack = findViewById(R.id.button_back)
        this.tvTitle = findViewById(R.id.tv_title)
        this.tvDay = findViewById(R.id.tv_day)
        this.tvEmptyItem = findViewById(R.id.tv_empty_item)
        this.buttonAdd = findViewById(R.id.button_add)
        this.recyclerViewSchedule = findViewById(R.id.recyclerview_schedule)
        this.containerSearch = findViewById(R.id.container_search)
        this.tvinputSearch = findViewById(R.id.tvinput_search)
    }

    private fun updateView() {
        if (settings.customTheme.equals(ThemeSettings.DARK_THEME)){
            parentView.setBackgroundColor(resources.getColor(R.color.darkColorPrimary))
            layoutEditMenu.setBackground(resources.getDrawable(R.drawable.bg_menu_dark))
            buttonMenu.setBackgroundColor(resources.getColor(R.color.darkColorPrimary))
            buttonBack.setBackground(resources.getDrawable(R.drawable.ic_round_arrow_back_24))
            tvTitle.setTextColor(resources.getColor(R.color.darkColorOnPrimary))
            tvDay.setTextColor(resources.getColor(R.color.darkColorOnPrimary))
            tvEmptyItem.setTextColor(resources.getColor(R.color.darkColorOnPrimary))
            buttonAdd.setBackground(resources.getDrawable(R.drawable.ic_round_add_24))
            containerSearch.defaultHintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkColorOnPrimaryHint))
            containerSearch.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkColorSecondary))
            containerSearch.setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.darkColorOnPrimary)))
            containerSearch.setBoxStrokeColor(resources.getColor(R.color.darkColorSecondary))
            tvinputSearch.setTextColor(resources.getColor(R.color.darkColorOnPrimary))
        } else {
            parentView.setBackgroundColor(resources.getColor(R.color.lightColorPrimary))
            layoutEditMenu.setBackground(resources.getDrawable(R.drawable.bg_menu_light))
            buttonMenu.setBackgroundColor(resources.getColor(R.color.lightColorPrimary))
            buttonBack.setBackground(resources.getDrawable(R.drawable.ic_round_arrow_back_24_dark))
            tvTitle.setTextColor(resources.getColor(R.color.lightColorOnPrimary))
            tvDay.setTextColor(resources.getColor(R.color.lightColorOnPrimary))
            tvEmptyItem.setTextColor(resources.getColor(R.color.lightColorOnPrimary))
            buttonAdd.setBackground(resources.getDrawable(R.drawable.ic_round_add_24_dark))
            containerSearch.defaultHintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.lightColorOnPrimaryHint))
            containerSearch.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.lightColorSecondary))
            containerSearch.setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.lightColorOnPrimary)))
            containerSearch.setBoxStrokeColor(resources.getColor(R.color.lightColorSecondary))
            tvinputSearch.setTextColor(resources.getColor(R.color.lightColorOnPrimary))
        }
    }

    fun startEditFrag() {
        val buttonAdd = findViewById<ImageButton>(R.id.button_add)
        val fragEdit = FragEdit()

        if (layoutEditMenu.isVisible == false) {
            buttonAdd.setOnClickListener() {

                layoutEditMenu.setVisibility(View.VISIBLE)

                val buttonEditMenu = findViewById<Button>(R.id.button_edit_menu)

                buttonEditMenu.setOnClickListener() {
                    supportFragmentManager.beginTransaction().apply {
                        if (fragEdit.isAdded) {
                            remove(fragEdit)
                            commit()
                            layoutEditMenu.setVisibility(View.GONE)
                        }
                    }
                }

                supportFragmentManager.beginTransaction().apply {
                    if (fragEdit.isVisible == false) {
                        add(R.id.scrollview_edit_menu, fragEdit, "frag_add")
                        commit()
                    }
                }
            }
        }
    }

    fun setVarScheduleViewModel(context: Context) {
        this.scheduleViewModel = ScheduleViewModel(ListSchedule(context), this)
    }

    fun getVarScheduleViewModel() : ScheduleViewModel {
        return this.scheduleViewModel
    }

    override fun onDeleteClickListener(id: Int) {
        val data = this.scheduleViewModel.getDataById(id)
        this.scheduleViewModel.deleteData(data[0])
        Toast.makeText(this, data[0].desc + " has been deleted", Toast.LENGTH_LONG).show()
    }

    override fun onEditClickListener(id: Int) {
        val fragEdit = FragEdit()
        val data = this.scheduleViewModel.getDataById(id)

        if (layoutEditMenu.isVisible) {
            if (supportFragmentManager.findFragmentByTag("frag_add") !== null) {
                supportFragmentManager.beginTransaction().apply {
                    remove(supportFragmentManager.findFragmentByTag("frag_add")!!)
                    commit()
                }
            }
            if (supportFragmentManager.findFragmentByTag("frag_edit") !== null) {
                supportFragmentManager.beginTransaction().apply {
                    remove(supportFragmentManager.findFragmentByTag("frag_edit")!!)
                    commit()
                }
            }

            layoutEditMenu.setVisibility(View.GONE)
        }

        layoutEditMenu.setVisibility(View.VISIBLE)

        val buttonEditMenu = findViewById<Button>(R.id.button_edit_menu)

        buttonEditMenu.setOnClickListener() {
            supportFragmentManager.beginTransaction().apply {
                if (fragEdit.isAdded) {
                    remove(fragEdit)
                    commit()
                    layoutEditMenu.setVisibility(View.GONE)
                }
            }
        }

        supportFragmentManager.beginTransaction().apply {
            add(R.id.scrollview_edit_menu, fragEdit, "frag_edit")
            val bundle = Bundle()
            bundle.putInt("dataId", data[0].id)
            bundle.putString("dataDesc", data[0].desc)
            bundle.putString("dataTimeStart", data[0].timeStart)
            bundle.putString("dataTimeEnd", data[0].timeEnd)
            fragEdit.setArguments(bundle)
            commit()
        }
    }

    override fun onThemeApply(holder: ScheduleAdapter.ScheduleViewHolder) {
        if (settings.customTheme.equals(ThemeSettings.DARK_THEME)){
            holder.itemContainer.setBackground(resources.getDrawable(R.drawable.bg_item_dark))
            holder.tvDesc.setTextColor(resources.getColor(R.color.darkColorOnSecondary))
            holder.tvTime.setTextColor(resources.getColor(R.color.darkColorOnSecondary))
            holder.buttonEdit.setBackground(resources.getDrawable(R.drawable.ic_outline_edit_24_dark))
            holder.buttonDelete.setBackground(resources.getDrawable(R.drawable.ic_outline_delete_24_dark))
        } else {
            holder.itemContainer.setBackground(resources.getDrawable(R.drawable.bg_item))
            holder.tvDesc.setTextColor(resources.getColor(R.color.lightColorOnSecondary))
            holder.tvTime.setTextColor(resources.getColor(R.color.lightColorOnSecondary))
            holder.buttonEdit.setBackground(resources.getDrawable(R.drawable.ic_outline_edit_24))
            holder.buttonDelete.setBackground(resources.getDrawable(R.drawable.ic_outline_delete_24))
        }
    }

    override fun scheduleNotification(data : Schedule) {
        val intent = Intent(applicationContext, Notification::class.java)
        val dataId = data.id
        intent.putExtra("channelID", "channel$dataId")
        intent.putExtra("notificationID", dataId)

        val day = data.day
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
        val title = "RemindMe $day"

        val desc = data.desc
        val startTime = data.timeStart
        val endTime = data.timeEnd
        val message = "$desc, $startTime - $endTime"

        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            dataId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, enumDay)
        calendar.set(Calendar.HOUR_OF_DAY, startTime.split(":")[0].toInt())
        calendar.set(Calendar.MINUTE, startTime.split(":")[1].toInt())

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY * 7,
            pendingIntent
        )
    }

    override fun deleteScheduleNotification(id : Int) {
        val data = this.scheduleViewModel.getDataById(id)
        val id = data[0].id

        val intent = Intent(this, Notification::class.java)
        val sender = PendingIntent.getBroadcast(this, id, intent, 0)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(sender)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotificationChannel(data : Schedule) {
        val id = data.id

        val name = "RemindMe Channel"
        val desc = data.desc
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("channel$id", name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}