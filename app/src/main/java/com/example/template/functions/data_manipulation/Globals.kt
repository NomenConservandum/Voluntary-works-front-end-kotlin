package com.example.template.functions.data_manipulation

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.MutableLiveData
import com.example.template.model.User

val globalToken: MutableLiveData<String> = MutableLiveData()
val globalEmail: MutableLiveData<String> = MutableLiveData()
val globalChangeUser: MutableLiveData<User> = MutableLiveData()
val globalDeleteUser: MutableLiveData<User> = MutableLiveData()
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")