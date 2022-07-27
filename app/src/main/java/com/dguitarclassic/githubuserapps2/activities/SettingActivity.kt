package com.dguitarclassic.githubuserapps2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.dguitarclassic.githubuserapps2.theme.PrefHelper
import com.dguitarclassic.githubuserapps2.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    private val theme by lazy { PrefHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar!!.title = "Theme"

        switch_dark.isChecked = theme.getBoolean("pref_is_dark_mode")

        switch_dark.setOnCheckedChangeListener { compoundButton, isChecked ->
            when(isChecked) {
                true -> {
                    theme.change("pref_is_dark_mode", true)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                false -> {
                    theme.change("pref_is_dark_mode", false)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }
}