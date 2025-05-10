package com.example.template.functions.navigation

import android.content.Context
import android.content.Intent
import com.example.template.DevMainMenu
import com.example.template.LoginPage
import com.example.template.SignUpPage
import com.example.template.CRUDMenu
import com.example.template.MyProfilePage
import com.example.template.StudentFeedPage

fun tologinpage(context: Context) {
    val intent = Intent(context, LoginPage::class.java)
    context.startActivity(intent)
}
fun tosignuppage(context: Context) {
    val intent = Intent(context, SignUpPage::class.java)
    context.startActivity(intent)
}
fun todevmenupage(context: Context) {
    val intent = Intent(context, DevMainMenu::class.java)
    context.startActivity(intent)
}
fun tocrudmenupage(context: Context) {
    val intent = Intent(context, CRUDMenu::class.java)
    context.startActivity(intent)
}
fun tofeedpage(context: Context) {
    val intent = Intent(context, StudentFeedPage::class.java)
    context.startActivity(intent)
}
fun tomyprofilepage(context: Context) {
    val intent = Intent(context, MyProfilePage::class.java)
    context.startActivity(intent)
}

fun navigationhub(context: Context, role: String) {
    when (role) {
        "Dev" -> todevmenupage(context)
        "Admin" -> tocrudmenupage(context)
        "Student" -> tofeedpage(context)
        else -> tosignuppage(context)
    }
}