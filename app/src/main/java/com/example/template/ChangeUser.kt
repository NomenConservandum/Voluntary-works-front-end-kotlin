package com.example.template

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.template.functions.data_manipulation.globalChangeUser
import com.example.template.model.User
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.template.functions.checkForInternet
import com.example.template.functions.navigation.navigationhub
import com.example.template.functions.navigation.tosignuppage
import com.example.template.functions.removespaces
import com.example.template.repository.Repository
import com.example.template.viewModel.MainViewModel
import com.example.template.viewModelFactory.MainViewModelFactory
import androidx.lifecycle.Observer

class ChangeUser : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    lateinit var toChange: User
    lateinit var oldEmail: String

    lateinit var inemail: EditText
    lateinit var inpassword: EditText
    lateinit var infirstname: EditText
    lateinit var insecondname: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_user)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        inemail = findViewById(R.id.change_email)
        inpassword = findViewById(R.id.change_password)
        infirstname = findViewById(R.id.change_firstname)
        insecondname = findViewById(R.id.change_secondname)
        toChange = globalChangeUser.value ?: User(0, "", "", "", "")
        oldEmail = globalChangeUser.value?.email ?: ""

        inemail.setText(globalChangeUser.value?.email)
        inpassword.setText("")
        infirstname.setText(globalChangeUser.value?.firstName)
        insecondname.setText(globalChangeUser.value?.secondName)
    }

    fun change(view: View?) {
        if (removespaces(inemail.text.toString()) == "" ||
            removespaces(inpassword.text.toString()) == "" ||
            removespaces(infirstname.text.toString()) == "" ||
            removespaces(insecondname.text.toString()) == "") {
            Toast.makeText(this, "You have an empty field", Toast.LENGTH_SHORT).show()
            return
        }
        if (checkForInternet(this).not()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
            return
        }

        viewModel.edit(
            User(
                globalChangeUser.value?.id ?: 0,
                inemail.text.toString(),
                inpassword.text.toString(),
                infirstname.text.toString(),
                insecondname.text.toString()
            ),
            oldEmail
        )

        // observe
        viewModel.myString.observe(this, Observer {
                response ->
            if (response == "SUCCESS") {
                navigationhub(this, "CRUD MENU")
                this.finish()
                Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.myErrorCodeResponse.observe(this, Observer {
                response ->
            if (response == 422) {
                Toast.makeText(this, "ERROR: invalid input", Toast.LENGTH_SHORT).show()
                tosignuppage(this)
            } else if (response != 201) {
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