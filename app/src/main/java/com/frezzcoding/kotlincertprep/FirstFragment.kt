package com.frezzcoding.kotlincertprep

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_one.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FirstFragment : Fragment(R.layout.fragment_one) {

    private val TAG: String = FirstFragment::class.java.simpleName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_select_theme.setOnClickListener {
            chooseThemeDialog()
        }
        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE) ?: return
        println("here")
        println(sharedPref)
        with(sharedPref.edit()){
            putInt(getString(R.string.preference_file_key), 24)
            apply()
        }
        val defaultValue = 0
        val highScore = sharedPref.getInt(getString(R.string.preference_file_key), defaultValue)

        println(highScore)


        //initialize datastore
        val dataStore: DataStore<androidx.datastore.preferences.core.Preferences> = requireContext().createDataStore(name = "settings")
        //read from the data store
        val counter = preferencesKey<Int>("counter")
        val counterFlow : Flow<Int> = dataStore.data.map {
            it[counter] ?: 0
        }

        //call this on suspend
        dataStore.edit { settings ->
            val currentCounterValue = settings[counter] ?: 0
            settings[counter] = currentCounterValue + 1
        }

    }

    private fun chooseThemeDialog() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("choose theme")
        val styles = arrayOf("Light","Dark","System default")
        val checkedItem = 0
        Log.d(TAG, "choosing theme dialog")
        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    (requireActivity() as LearningUIActivity).delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    (requireActivity() as LearningUIActivity).delegate.applyDayNight()
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    (requireActivity() as LearningUIActivity).delegate.applyDayNight()
                    dialog.dismiss()
                }

            }
        }

        val dialog = builder.create()
        dialog.show()
    }

}