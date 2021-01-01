package com.frezzcoding.kotlincertprep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.custom_toast.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //what to cover
        //todo toasts
        showToast()
        //todo snackbars
        showSnackbar()
        //todo localisation
        //covered localisation
        //todo app fundamentals
        //broadcast receivers - send a notification if battery is low or picture was captured
        //services
        //content providers
        //todo implicit intents
        appSelector()
        //todo create notification
        //todo androidx overview
        //todo getting started with jetpack
        //todo android ktx
        //todo codelabs workmanager
        //todo codelabs notifications

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