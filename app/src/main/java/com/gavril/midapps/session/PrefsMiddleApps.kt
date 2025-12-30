package com.gavril.midapps.session

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PrefsMiddleApps(context: Context) {

    companion object {
        const val PREF_NAME = "middle_apps_prefs"
        const val KEY_COUNT = "last_count"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getCount(): Int {
        return sharedPreferences.getInt(KEY_COUNT,0)
    }

    fun saveCount(newCount: Int) {
        sharedPreferences.edit(){ putInt(KEY_COUNT, newCount) }
    }
}