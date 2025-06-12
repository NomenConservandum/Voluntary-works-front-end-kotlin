package com.example.template.functions.data_manipulation
// Functions to manipulate user data (configuration files, requests, etc.)

import android.content.Context
import com.example.template.functions.navigation.tologoutpage
import com.example.template.functions.navigation.tosignuppage
import com.example.template.preferencesManager.AuthManager

fun logout(context: Context) {
    // yeah, really weird obfuscation right here...
    tologoutpage(context)
}