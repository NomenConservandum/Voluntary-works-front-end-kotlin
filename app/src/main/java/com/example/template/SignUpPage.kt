package com.example.template

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.template.repository.Repository
import com.example.template.viewModel.MainViewModel
import com.example.template.viewModelFactory.MainViewModelFactory
import com.example.template.functions.*
import com.example.template.functions.data_manipulation.globalEmail
import com.example.template.preferencesManager.AuthManager
import com.example.template.functions.data_manipulation.globalToken
import com.example.template.functions.navigation.*

class SignUpPage : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    // 'in' prefix for 'input'
    lateinit var inEmail: EditText
    lateinit var inPassword: EditText
    lateinit var inFirstname: EditText
    lateinit var inSecondname: EditText
    lateinit var inPatronymic: EditText
    lateinit var inTelegramURL: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        inEmail = findViewById(R.id.email_input)
        inPassword = findViewById(R.id.password_input)
        inFirstname = findViewById(R.id.surname_input)
        inSecondname = findViewById(R.id.name_input)
        inPatronymic = findViewById(R.id.patronymic_input)
        inTelegramURL = findViewById(R.id.telegram_input)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }
    fun signup(view: View?) {
        if (removespaces(inEmail.text.toString()) == "" ||
            removespaces(inPassword.text.toString()) == "" ||
            removespaces(inFirstname.text.toString()) == "" ||
            removespaces(inSecondname.text.toString()) == "" ||
            removespaces(inPatronymic.text.toString()) == "" ||
            removespaces(inTelegramURL.text.toString()) == ""
            ) {
            Toast.makeText(this, "You have an empty field", Toast.LENGTH_SHORT).show()
            return
        }
        if (checkForInternet(this).not()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
            return
        }

        val authman = AuthManager()
        authman.writeEmail(inEmail.text.toString(), this)
        globalEmail.value = inEmail.text.toString()

        // server API request
        viewModel.register(
            removespaces(inEmail.text.toString()),
            removespaces(inPassword.text.toString()),
            removespaces(inFirstname.text.toString()),
            removespaces(inSecondname.text.toString()),
            removespaces(inPatronymic.text.toString()),
            removespaces(inTelegramURL.text.toString())
        )

        viewModel.myDataResponse.observe(this, Observer {
                response ->
            Toast.makeText(this, R.string.welcome, Toast.LENGTH_SHORT).show()
            globalToken.value = response?.body()!!.data
            authman.writeToken(globalToken.value.toString(), this)

            authman.writeToken(globalToken.value.toString(), this)
            if (response.body() != null) {
                navigationhub(this, "CRUD MENU")
                this.finish()
            }
        })
        viewModel.myErrorCodeResponse.observe(this, Observer {
                response ->
            if (response != 201) {
                Toast.makeText(this, "ERROR: ".plus(response.toString()), Toast.LENGTH_SHORT).show()
                if (response == null) {
                    Toast.makeText(this, "No Response", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    fun tologinpage(view: View?) {
        tologinpage(this)
        this.finish()
    }
}