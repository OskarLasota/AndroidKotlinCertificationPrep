package com.frezzcoding.kotlincertprep

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        println("onReceive")
        when(intent?.action){
            "ACTION_SNOOZE" -> println("pressed snooze")
            BatteryManager.EXTRA_BATTERY_LOW -> println("battery low")

        }
    }
}