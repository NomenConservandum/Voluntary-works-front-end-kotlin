package com.example.template.preferencesManager

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.template.functions.data_manipulation.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class AuthManager {
    val TOKEN_KEY = stringPreferencesKey("jwt_token")
    val EMAIL_KEY = stringPreferencesKey("email")
    fun writeToken(token: String, context: Context) {
        runBlocking(Dispatchers.IO) { // what a mess...
            context.dataStore.edit { settings ->
                settings[TOKEN_KEY] = token
            }
        }
    }

    fun readToken(context: Context): String {
        var retString: String
        val retValue: Flow<String> = context.dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[TOKEN_KEY] ?: ""
            }
        runBlocking(Dispatchers.IO) {
            retString = retValue.first()
        }
        return retString // TODO: check this one
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
        return retString // TODO: check this one
    }
}