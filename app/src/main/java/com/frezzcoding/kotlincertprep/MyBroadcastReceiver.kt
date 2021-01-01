package com.frezzcoding.kotlincertprep

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        println("onReceive")
        when(intent?.action){
            "ACTION_SNOOZE" -> println("pressed snooze")
        }
    }
}