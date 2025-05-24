package com.example.template.functions.data_manipulation
// Functions to manipulate user data (configuration files, requests, etc.)

import android.content.Context
import com.example.template.functions.navigation.tosignuppage
import com.example.template.preferencesManager.AuthManager


fun logout(context: Context) {
    globalToken.value = ""
    val authman = AuthManager()
    authman.writeToken("", context)
    authman.writeEmail("", context)
    authman.writeAssignitions(emptyList(), context)
    globalEmail.value = ""
    tosignuppage(context)
    globalEmail.value = ""
}

/*
LEGACY
import java.io.File

// Hash manipulation functions
fun readhash(context: Context): String {
    return File(context.cacheDir.path, "hash").readText(Charsets.UTF_8)
}
fun writehash(context: Context, hash: String) {
    File(context.cacheDir.path, "hash").writeText(hash)
}
 */