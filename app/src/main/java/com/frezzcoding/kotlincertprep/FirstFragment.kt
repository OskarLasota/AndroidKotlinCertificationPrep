package com.frezzcoding.kotlincertprep

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_one.*

class FirstFragment : Fragment(R.layout.fragment_one) {

    private val TAG: String = FirstFragment::class.java.simpleName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_select_theme.setOnClickListener {
            chooseThemeDialog()
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