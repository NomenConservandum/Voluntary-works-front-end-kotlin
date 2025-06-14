package com.example.template

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.template.repository.Repository
import com.example.template.viewModel.MainViewModel
import com.example.template.viewModelFactory.MainViewModelFactory

class MyProfilePage : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var myprofile_name: TextView
    private lateinit var myprofile_email: TextView
    private lateinit var myprofile_tgurl: TextView
    //private lateinit var myprofile_string: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile_page)

        myprofile_name = findViewById(R.id.myprofile_name)
        myprofile_email = findViewById(R.id.myprofile_email)
        myprofile_tgurl = findViewById(R.id.myprofile_tgurl)
        //myprofile_string = findViewById(R.id.myprofile_temp_string)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getMyProfile()

        viewModel.myUserResponse.observe(this, Observer {
            response ->
            myprofile_name.text = response.name
            myprofile_email.text = response.email
            myprofile_tgurl.text = response.telegramUrl
            //myprofile_string.text = response.toString()
        })

    }
}