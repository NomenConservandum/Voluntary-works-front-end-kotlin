package com.example.template.functions.navigation

import android.content.Context
import android.content.Intent
import com.example.template.DevMainMenu
import com.example.template.LoginPage
import com.example.template.SignUpPage
import com.example.template.CRUDMenu

fun tologinpage(context: Context) {
    // setContentView(R.layout.activity_login_page)
    val intent = Intent(context, LoginPage::class.java)
    context.startActivity(intent)
    //this.finish()
}
fun tosignuppage(context: Context) {
    // setContentView(R.layout.activity_sign_up_page)
    val intent = Intent(context, SignUpPage::class.java)
    context.startActivity(intent)
    //this.finish()
}
fun todevmenupage(context: Context) {
    // setContentView(R.layout.activity_sign_up_page)
    val intent = Intent(context, DevMainMenu::class.java)
    context.startActivity(intent)
    //this.finish()
}
fun tocrudmenupage(context: Context) {
    // setContentView(R.layout.activity_sign_up_page)
    val intent = Intent(context, CRUDMenu::class.java)
    context.startActivity(intent)
    //this.finish()
}

fun navigationhub(context: Context, role: String) {
    when (role) {
        "Dev" -> todevmenupage(context)
        "Admin" -> println("no such menu yet")
        "MAIN MENU" -> println("no such menu yet")
        "Student" -> tocrudmenupage(context) // temporary
        else -> tosignuppage(context)
    }
}