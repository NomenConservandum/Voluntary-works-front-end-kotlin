package com.example.template

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.template.model.PrivateRequest
import com.example.template.repository.Repository
import com.example.template.viewModel.MainViewModel
import com.example.template.viewModelFactory.MainViewModelFactory
import java.time.LocalDateTime

class CreateRequestPage : AppCompatActivity() {
    lateinit var address: TextView
    lateinit var days_str: TextView
    lateinit var people: TextView
    lateinit var points: TextView
    lateinit var description: TextView
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_request_page)
        address = findViewById(R.id.address_input)
        days_str = findViewById(R.id.date_input)
        people = findViewById(R.id.people_input)
        points = findViewById(R.id.points_input)
        description = findViewById(R.id.description_input)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun create(view: View?) {
        val days = days_str.text.toString().toLong()
        var date = LocalDateTime.now()
        val deadline = date.plusDays(days)
        var date_str = date.toString().split('.')[0] + ".014Z"
        val deadline_str = deadline.toString().split('.')[0] + ".014Z"
        val privateRequest = PrivateRequest(
            0,
            0,
            address.text.toString(),
            date_str,
            deadline_str,
            points.text.toString().toInt(),
            mutableListOf<Int>(),
            people.text.toString().toInt(),
            description.text.toString(),
            false,
            false,
            ""
        )
        viewModel.createRequest(privateRequest)
        viewModel.myErrorCodeResponse.observe(this, Observer {
                code ->
            if (code != 201) {
                Toast.makeText(this, "ERROR: ".plus(code.toString()), Toast.LENGTH_SHORT).show()
                if (code == null)
                    Toast.makeText(this, "No Response", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.myString.observe(this, Observer {
                value ->
            if (value == "CREATED_REQUEST_SUCCESSFULLY")
                Toast.makeText(this, "The request has been created successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AdminFeedPage::class.java)
                startActivity(intent)
        })
    }
}