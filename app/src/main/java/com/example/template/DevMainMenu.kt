package com.example.template

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.template.functions.data_manipulation.*
import com.example.template.functions.data_manipulation.globalToken
import com.example.template.functions.navigation.navigationhub


class DevMainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dev_main_menu)
        Toast.makeText(this, globalToken.value, Toast.LENGTH_SHORT).show()
    }
    fun logout(view: View?) {
        logout(this)
        this.finish()
    }
    fun topromotepage(view: View?) {
        val intent = Intent(this, DevPromote::class.java)
        startActivity(intent)
    }
    fun todemotepage(view: View?) {
        val intent = Intent(this, DevDemote::class.java)
        startActivity(intent)
    }
    fun toAdminPage(view: View?) {
        navigationhub(this, "Admin")
        this.finish()
    }
}