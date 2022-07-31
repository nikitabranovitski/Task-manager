package com.branovitski.taskmanager

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.branovitski.taskmanager.databinding.ActivityTaskManagerBinding
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import io.karn.notify.Notify
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class TaskManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskManagerBinding

    private val navigator = AppNavigator(this, R.id.container)

    @Inject
    lateinit var navigatorHolder: NavigatorHolder


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigator.applyCommands(arrayOf<Command>(Replace(Screens.AllNotes())))
        setAlarm(this)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("UnspecifiedImmutableFlag")
    fun setAlarm(context: Context) {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 19
        calendar[Calendar.MINUTE] = 46
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        val cur = Calendar.getInstance()
        if (cur.after(calendar)) {
            calendar.add(Calendar.DATE, 1)
        }
        val myIntent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            myIntent,
            FLAG_MUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}