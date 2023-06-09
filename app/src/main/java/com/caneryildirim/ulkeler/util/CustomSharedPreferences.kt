package com.caneryildirim.ulkeler.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import javax.inject.Inject

class CustomSharedPreferences @Inject constructor() {

    companion object{
        private val PREFERENCE_TIME="Preference_Time"
        private var sharedPreferences: SharedPreferences?=null

        @Volatile private var instance:CustomSharedPreferences?=null
        private val lock=Any()

        operator fun invoke(context: Context):CustomSharedPreferences= instance?: synchronized(lock){
            instance?: makeCustomSharedPreferences(context).also {
                instance=it
            }
        }

        private fun makeCustomSharedPreferences(context: Context):CustomSharedPreferences{
            sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }

    }

    fun saveTime(time:Long){
        sharedPreferences?.edit(commit = true){
            putLong(PREFERENCE_TIME,time)
        }
    }

    fun getTime() = sharedPreferences?.getLong(PREFERENCE_TIME,0)

}