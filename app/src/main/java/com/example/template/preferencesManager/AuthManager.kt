package com.example.template.preferencesManager

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.template.functions.data_manipulation.dataStore
import com.example.template.functions.data_manipulation.globalAssignedIDs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class AuthManager {
    val REFRESH_TOKEN_KEY = stringPreferencesKey("jwt_refresh_token")
    val ACCESS_TOKEN_KEY = stringPreferencesKey("jwt_access_token")
    val EMAIL_KEY = stringPreferencesKey("email")
    val ASSIGNED_KEY = stringPreferencesKey("assigned_array")

    fun readAssignitions(context: Context): List<String> {

        globalAssignedIDs.clear()
        var retString: List<String>
        val retValue: Flow<String> = context.dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[ASSIGNED_KEY] ?: ""
            }

        runBlocking(Dispatchers.IO) {
            if (retValue.first() == "")
                retString = emptyList()
            retString = retValue.first().split(';')
        }
        if (retString.isEmpty() || retString[0] == "")
            return emptyList()
        // convert to Int and write globally
        var result: List<Int> = emptyList()
        for (item in retString)
            result = result.plus(item.toInt())
        globalAssignedIDs.addAll(result)

        return retString
    }

    fun addAssignition(id: Int, context: Context) {

        globalAssignedIDs.clear()
        var list: List<String>
        list = readAssignitions(context)
        var newLst: List<String>
        newLst = list
        if (!list.contains(id.toString()))
            newLst = list.plus(id.toString())
        writeAssignitions(newLst, context)
    }
    fun removeAssignition(id: Int, context: Context) {

        globalAssignedIDs.clear()
        var list: List<String>
        list = readAssignitions(context)
        var newLst: List<String>
        newLst = list.minus(id.toString())
        writeAssignitions(newLst, context)
    }

    fun writeAssignitions(list: List<String>, context: Context) {

        globalAssignedIDs.clear()
        if (list.isEmpty()) {
            runBlocking(Dispatchers.IO) { // what a mess...
                context.dataStore.edit { settings ->
                    settings[ASSIGNED_KEY] = ""
                }
            }
            return
        }
        var resStr = list[0]
        var newlst = list.minus(list[0])
        for (item in newlst)
            resStr += ';' + item
        runBlocking(Dispatchers.IO) { // what a mess...
            context.dataStore.edit { settings ->
                settings[ASSIGNED_KEY] = resStr
            }
        }
        Log.i("ASSIGNITIONS", resStr)
    }


    fun writeRefreshToken(token: String, context: Context) {
        runBlocking(Dispatchers.IO) { // what a mess...
            context.dataStore.edit { settings ->
                settings[REFRESH_TOKEN_KEY] = token
            }
        }
    }

    fun readRefreshToken(context: Context): String {
        var retString: String
        val retValue: Flow<String> = context.dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[REFRESH_TOKEN_KEY] ?: ""
            }
        runBlocking(Dispatchers.IO) {
            retString = retValue.first()
        }
        return retString
    }

    fun writeAccessToken(token: String, context: Context) {
        runBlocking(Dispatchers.IO) { // what a mess...
            context.dataStore.edit { settings ->
                settings[ACCESS_TOKEN_KEY] = token
            }
        }
    }

    fun readAccessToken(context: Context): String {
        var retString: String
        val retValue: Flow<String> = context.dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[ACCESS_TOKEN_KEY] ?: ""
            }
        runBlocking(Dispatchers.IO) {
            retString = retValue.first()
        }
        return retString
    }

    fun writeEmail(email: String, context: Context) {
        runBlocking(Dispatchers.IO) { // what a mess...
            context.dataStore.edit { settings ->
                settings[EMAIL_KEY] = email
            }
        }
    }
    fun readEmail(context: Context): String {
        var retString: String
        val retValue: Flow<String> = context.dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[EMAIL_KEY] ?: ""
            }
        runBlocking(Dispatchers.IO) {
            retString = retValue.first()
        }
        return retString
    }
}