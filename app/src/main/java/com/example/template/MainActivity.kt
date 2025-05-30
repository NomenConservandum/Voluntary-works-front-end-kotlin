package com.example.template

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.template.functions.data_manipulation.globalAssignedIDs
import com.example.template.functions.data_manipulation.globalEmail
import com.example.template.functions.data_manipulation.globalRole
import com.example.template.preferencesManager.AuthManager
import com.example.template.functions.data_manipulation.globalToken
import com.example.template.functions.navigation.navigationhub
import com.example.template.functions.navigation.tosignuppage
import com.example.template.repository.Repository
import com.example.template.viewModel.MainViewModel
import com.example.template.viewModelFactory.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var progresstext: TextView
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        progresstext = findViewById(R.id.progresstext)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        super.onCreate(savedInstanceState)

        globalToken.value = ""
        // hash reading
        progresstext.setText(R.string.loading)
        val authman = AuthManager()
        try {
            globalToken.value = authman.readToken(this)
            globalEmail.value = authman.readEmail(this)
            authman.readAssignitions(this)
        } catch (e: Exception) {
            authman.writeToken("", this)
            authman.writeEmail("", this)
            authman.writeAssignitions(emptyList(), this)
            // The user has opened the app for the first time creating the field
            globalToken.value = authman.readToken(this)
            globalEmail.value = authman.readEmail(this)
        }

        globalToken.observe(this, Observer {
            progresstext.setText(R.string.changing_layout)
            viewModel.check()
        })

        var think = false;

        viewModel.myDataResponse.observe(this, Observer {
                response ->
            if (response?.code() == 201 || response?.code() == 200 && !think) {
                globalRole.value = response.body()?.data ?: ""
                Toast.makeText(this, "The role is ".plus(globalRole.value), Toast.LENGTH_SHORT).show()
                think = true;
                navigationhub(this, globalRole.value.toString())
                this.finish()
            }
        })
        viewModel.myErrorCodeResponse.observe(this, Observer {
                response ->
            if (response == 401) {
                Toast.makeText(this, "ERROR: invalid token", Toast.LENGTH_SHORT).show()
                tosignuppage(this)
            } else if (response != 200 && response != 201) {
                Toast.makeText(this, "ERROR: ".plus(response.toString()), Toast.LENGTH_SHORT).show()
                if (response == null) {
                    Toast.makeText(this, "No Response", Toast.LENGTH_SHORT).show()
                }
                tosignuppage(this)
            }
            this.finish()
        })
    }
}