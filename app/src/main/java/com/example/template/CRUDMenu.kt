package com.example.template

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.template.preferencesManager.AuthManager
import com.example.template.functions.data_manipulation.globalAccessToken
import com.example.template.functions.data_manipulation.logout

class CRUDMenu : AppCompatActivity() {
    val authman = AuthManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crudmenu)

        val contextTemp: Context = this
        globalAccessToken.value = authman.readAccessToken(contextTemp)
        //Toast.makeText(this, globalToken.value, Toast.LENGTH_SHORT).show()
    }
    fun toCreateUserPage(view: View?) {
        val intent = Intent(this, CreateUser::class.java)
        startActivity(intent)
    }
    fun toGetUserPage(view: View?) {
        val intent = Intent(this, GetUser::class.java)
        startActivity(intent)
    }
    fun toGetUsersPage(view: View?) {
        val intent = Intent(this, GetUsers::class.java)
        startActivity(intent)
    }
    fun toChangeUserPage(view: View?) {
        val intent = Intent(this, ChangeUser::class.java)
        startActivity(intent)
    }
    fun toDeleteUserPage(view: View?) {
        val intent = Intent(this, DeleteUser::class.java)
        startActivity(intent)
    }
    fun toFeedPage(view: View?) {
        val intent = Intent(this, AdminFeedPage::class.java)
        startActivity(intent)
    }
    fun toCreateRequestPage(view: View?) {
        val intent = Intent(this, CreateRequestPage::class.java)
        startActivity(intent)
    }
    fun toLogOut(view: View?) {
        logout(this)
        this.finish()
    }
}