package com.dguitarclassic.githubuserapps2.theme

import android.content.Context
import android.content.SharedPreferences

class PrefHelper (context: Context) {

    private val theme = "GithubUserApps2DarkMode"
    private var githubUserApps2DarkMode: SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        githubUserApps2DarkMode = context.getSharedPreferences(theme, Context.MODE_PRIVATE)
        editor = githubUserApps2DarkMode.edit()
    }

    fun change(key: String, value: Boolean) {
        editor.putBoolean(key, value)
            .apply()
    }

    fun getBoolean(key: String): Boolean {
        return githubUserApps2DarkMode.getBoolean(key, false)
    }
}