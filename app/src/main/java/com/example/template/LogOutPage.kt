package com.example.template

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.template.functions.data_manipulation.globalAccessToken
import com.example.template.functions.data_manipulation.globalEmail
import com.example.template.functions.data_manipulation.globalRefreshToken
import com.example.template.functions.navigation.tosignuppage
import com.example.template.preferencesManager.AuthManager
import com.example.template.repository.Repository
import com.example.template.viewModel.MainViewModel
import com.example.template.viewModelFactory.MainViewModelFactory

class LogOutPage : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_out_page)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        process()
    }
    private fun process() {
        viewModel.revoke()

        viewModel.myString.observe(this, Observer {
                response ->
            if (response == "REVOKED_SUCCESSFULLY") {
                // Toast.makeText(this, "Tokens were refreshed successfully", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Revoked successfully", Toast.LENGTH_SHORT).show()
                globalAccessToken.value = ""
                globalRefreshToken.value = ""
                val authman = AuthManager()
                authman.writeAccessToken("", this)
                authman.writeRefreshToken("", this)
                authman.writeEmail("", this)
                authman.writeAssignitions(emptyList(), this)
                globalEmail.value = ""

                tosignuppage(this)
                this.finish()
            }
        })
        var think = false
        viewModel.myErrorCodeResponse.observe(this, Observer {
                response ->
            if (response == 401 && !think) { // we gotta refresh token
                Toast.makeText(this, "ERROR: refreshing token (i guess)", Toast.LENGTH_SHORT).show()
                viewModel.refreshTokens()
                viewModel.revoke()
                think = true // we're not coming back here again
            }
        })
    }
}