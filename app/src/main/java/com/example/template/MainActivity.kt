package com.example.template

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.template.functions.data_manipulation.globalEmail
import com.example.template.functions.data_manipulation.globalRole
import com.example.template.preferencesManager.AuthManager
import com.example.template.functions.data_manipulation.globalAccessToken
import com.example.template.functions.data_manipulation.globalRefreshToken
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

        globalAccessToken.value = ""
        globalRefreshToken.value = ""
        // hash reading
        progresstext.setText(R.string.loading)
        val authman = AuthManager()
        try {
            globalAccessToken.value = authman.readAccessToken(this)
            globalRefreshToken.value = authman.readRefreshToken(this)
            globalEmail.value = authman.readEmail(this)
            authman.readAssignitions(this)
        } catch (e: Exception) {
            authman.writeAccessToken("", this)
            authman.writeRefreshToken("", this)
            authman.writeEmail("", this)
            authman.writeAssignitions(emptyList(), this)
            // The user has opened the app for the first time creating the field
            globalAccessToken.value = authman.readAccessToken(this)
            globalRefreshToken.value = authman.readRefreshToken(this)
            globalEmail.value = authman.readEmail(this)
        }

        globalAccessToken.observe(this, Observer {
                value ->
            progresstext.setText(R.string.changing_layout)
            if (value.toString() != "")
                viewModel.check()
            else
                tosignuppage(this)
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
        viewModel.myTokensResponse.observe(this, Observer {
            tokens ->
            authman.writeAccessToken(tokens.body()!!.accessToken, this)
            authman.writeRefreshToken(tokens.body()!!.refreshToken, this)
            globalAccessToken.value = authman.readAccessToken(this)
            globalRefreshToken.value = authman.readRefreshToken(this)
            Toast.makeText(this, "Tokens were refreshed successfully", Toast.LENGTH_SHORT).show()
        })
        viewModel.myErrorCodeResponse.observe(this, Observer {
                response ->
            if (response == 401) {
                Toast.makeText(this, "ERROR: refreshing token", Toast.LENGTH_SHORT).show()
                // refresh the tokens
                viewModel.refreshTokens()
                //tosignuppage(this)
            } else if (response  == 404) {
                // the token is not valid => no such user.
                tosignuppage(this)
            }
            else if (response != 200 && response != 201) {
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