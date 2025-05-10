package com.example.template

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.template.functions.checkForInternet
import com.example.template.functions.navigation.navigationhub
import com.example.template.functions.removespaces
import com.example.template.repository.Repository
import com.example.template.viewModel.MainViewModel
import com.example.template.viewModelFactory.MainViewModelFactory

class DevDemote : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    // 'in' prefix for 'input'
    lateinit var inemail: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dev_demote)

        inemail = findViewById(R.id.input_email)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    fun demote(view: View?) {
        if (removespaces(inemail.text.toString()) == "") {
            Toast.makeText(this, "You have an empty field", Toast.LENGTH_SHORT).show()
            return
        }
        if (checkForInternet(this).not()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
            return
        }
        viewModel.demoteToStudent(removespaces(inemail.text.toString()))
        // observe
        viewModel.myString.observe(this, Observer {
                response ->
            if (response == "SUCCESS") {
                navigationhub(this, "Dev")
                this.finish()
                Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.myErrorCodeResponse.observe(this, Observer {
                response ->
            Toast.makeText(this, "ERROR: ".plus(response.toString()), Toast.LENGTH_SHORT).show()
            if (response == null) {
                Toast.makeText(this, "No Response", Toast.LENGTH_SHORT).show()
            }

            this.finish()
        })
    }
}