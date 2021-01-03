package com.frezzcoding.kotlincertprep

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.custom_toast.*

class MainActivity : AppCompatActivity() {


    private lateinit var layout : ConstraintLayout
    private var constraintSetShow = ConstraintSet()
    private var constraintSetHide = ConstraintSet()
    private var isShown = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layout = findViewById(R.id.layout)
        constraintSetHide.clone(layout)
        constraintSetShow.clone(this, R.layout.activity_main_detail)

        //what to cover
        //todo toasts
        showToast()
        //todo snackbars
        showSnackbar()
        //todo localisation
        //covered localisation
        //todo app fundamentals
        //broadcast receivers - send a notification if battery is low or picture was captured
        var batteryIntentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        var receiver = MyBroadcastReceiver()
        registerReceiver(receiver, batteryIntentFilter)
        //services - can be a music player that will continue the service in the background
        //foreground services - are ongoing operations in the foreground the users can interact with the service
        //background services - these services do not notify the user about the ongoing background task
        //bound services - behaviour of a client-server interface, the android app can send requests to the service to fetch results
        //content providers - used to provide data from one application to other on request
        //todo implicit intents
        appSelector()
        //todo create notification
        createNotificationChannel()
        showNotification()
        //todo androidx overview
        //covered
        //todo getting started with jetpack
        //covered
        //todo android ktx
        //covered
        //todo codelabs workmanager
        runWork()
    }



    fun handleClickAnimation(view: View) {
        TransitionManager.beginDelayedTransition(layout)
        if(isShown){
            constraintSetHide.applyTo(layout)
        }else{
            constraintSetShow.applyTo(layout)
        }
        isShown = !isShown
    }

    private fun runWork(){
        //work can be done once or periodically
        //constraints can be specified
        //work can be done in parallel
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val request = OneTimeWorkRequest.Builder(MyWorker::class.java).setConstraints(constraints).build()
        WorkManager.getInstance().enqueue(request)
    }

    private fun createNotificationChannel(){
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "R.string.channel_name"
            val descriptionText = "getString(R.string.channel_description)"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("0", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

    private fun showNotification(){
        //set up broadcast receiver to perform a job in the background so the action does not interrupt the app that's already open
        val snoozeIntent = Intent(this, MyBroadcastReceiver::class.java).apply {
            action = "ACTION_SNOOZE"
            putExtra("id",0 )
        }
        val snoozePendingIntent : PendingIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, 0)
        
        var builder = NotificationCompat.Builder(this, "0")
            .setSmallIcon(R.drawable.ic_android)
            .setContentTitle("textTitle")
            .setContentText("textContent")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line...")
                //.bigPicture()
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(R.drawable.ic_launcher_background, "Snooze", snoozePendingIntent)

        with(NotificationManagerCompat.from(this)){
            notify(0, builder.build())
        }

        //media control notification

        /*
        var notification = NotificationCompat.Builder(context, CHANNEL_ID)
            // Show controls on lock screen even when user hides sensitive content.
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.ic_stat_player)
            // Add media control buttons that invoke intents in your media service
            .addAction(R.drawable.ic_prev, "Previous", prevPendingIntent) // #0
            .addAction(R.drawable.ic_pause, "Pause", pausePendingIntent) // #1
            .addAction(R.drawable.ic_next, "Next", nextPendingIntent) // #2
            // Apply the media style template
            .setStyle(MediaNotificationCompat.MediaStyle()
                .setShowActionsInCompactView(1 /* #1: pause button \*/)
                .setMediaSession(mediaSession.getSessionToken()))
            .setContentTitle("Wonderful music")
            .setContentText("My Awesome Band")
            .setLargeIcon(albumArtBitmap)
            .build()

         */


    }

    private fun appSelector(){
        val sendIntent : Intent = Intent(Intent.ACTION_SEND)
        val title = "title"
        val chooser : Intent = Intent.createChooser(sendIntent, title)
        //verify the original intent will resolve to at least one activity
        if(sendIntent.resolveActivity(packageManager) != null){
            startActivity(chooser)
        }
    }

    private fun showSnackbar(){
        Snackbar.make(window.decorView, "This is a snickers Snackbar", Snackbar.LENGTH_SHORT).show()
    }


    private fun showToast(){
        val layout: View = layoutInflater.inflate(R.layout.custom_toast, custom_toast_container)
        val text: TextView = layout.findViewById(R.id.text)
        text.text = "This is a toaster!"

        with (Toast(applicationContext)) {
            setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            duration = Toast.LENGTH_LONG
            view = layout
            show()
        }

        val toast = Toast.makeText(applicationContext, "This is a toaster!", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0,0)
    }



}