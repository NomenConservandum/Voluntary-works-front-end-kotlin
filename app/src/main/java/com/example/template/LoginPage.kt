package com.example.template

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.template.functions.*
import com.example.template.functions.data_manipulation.*
import com.example.template.functions.navigation.*
import com.example.template.repository.Repository
import com.example.template.viewModel.MainViewModel
import com.example.template.viewModelFactory.MainViewModelFactory
import com.example.template.functions.navigation.tosignuppage
import com.example.template.preferencesManager.AuthManager

class LoginPage : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    // 'in' prefix for 'input'
    lateinit var inemail: EditText
    lateinit var inpassword: EditText

    val authman = AuthManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        inemail = findViewById(R.id.email_input)
        inpassword = findViewById(R.id.password_input)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    fun signin(view: View?) {
        if (removespaces(inemail.text.toString()) == "" ||
            removespaces(inpassword.text.toString()) == "") {
            Toast.makeText(this, "You have an empty field", Toast.LENGTH_SHORT).show()
            return
        }
        if (checkForInternet(this).not()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
            return
        }

        val authman = AuthManager()
        authman.writeEmail(inemail.text.toString(), this)
        globalEmail.value = inemail.text.toString()

        // server check
        viewModel.login(
            removespaces(inemail.text.toString()),
            removespaces(inpassword.text.toString())
        )

        viewModel.myDataResponse.observe(this, Observer {
                response ->
            Toast.makeText(this, R.string.welcome_back, Toast.LENGTH_SHORT).show()
            globalToken.value = response?.body()!!.data
            authman.writeToken(globalToken.value.toString(), this)
            if (response.body() != null) {
                navigationhub(this, "CRUD MENU")
                this.finish()
            }
        })
        viewModel.myErrorCodeResponse.observe(this, Observer {
                response ->
            if (response == 401) {
                Toast.makeText(this, "ERROR: invalid email or password", Toast.LENGTH_SHORT).show()
            } else if (response != 200) {
                Toast.makeText(this, "ERROR: ".plus(response.toString()), Toast.LENGTH_SHORT).show()
                if (response == null) {
                    Toast.makeText(this, "No Response", Toast.LENGTH_SHORT).show()
                }
            }
        })

        /*
        // should I put it before the response observer?
        globalToken.observe(this, Observer {
            // Toast.makeText(this, "Success".plus(globalToken.value), Toast.LENGTH_SHORT).show()
            if (globalToken.value != "") {
                navigationhub(this, "CRUD MENU")
                this.finish()
            }
        })
         */
        /*
        sessionHash.observe(this, Observer {
            Toast.makeText(this, "Asking for the role", Toast.LENGTH_SHORT).show()
            // viewModel.getRole()
            //navigationhub(this, userrole.value!!)
            //this.finish()
            viewModel.myStringResponse.observe(this, Observer {
                    response ->
                if (response.code() != 200) {
                    Toast.makeText(this, "Error: ".plus(response.code()), Toast.LENGTH_SHORT).show()
                    viewModel.getRole()
                    /*
                    intent = Intent(this, SignUpPage::class.java)
                    startActivity(intent)
                    this.finish()*/
                } else { // TODO: some problem with fetching a role
                    Toast.makeText(this, "role is ".plus(response.body()), Toast.LENGTH_SHORT).show()
                    userrole.value = response.body()
                }
            })
        })

        userrole.observe(this, Observer {
            //progresstext.setText(R.string.changing_layout)
            if (userrole.value != null) {
                navigationhub(this, userrole.value!!)
                this.finish()
            }
        })
        LEGACY
         */

    }
    fun tosignuppage(view: View?) {
        tosignuppage(this)
        this.finish()
    }
}