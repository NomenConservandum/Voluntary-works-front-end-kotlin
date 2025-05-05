package com.example.template

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.template.functions.checkForInternet
import com.example.template.functions.data_manipulation.*
import com.example.template.functions.fullname
import com.example.template.functions.removespaces
import com.example.template.repository.Repository
import com.example.template.viewModel.MainViewModel
import com.example.template.viewModelFactory.MainViewModelFactory

class DevSignUp : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    // 'in' prefix for 'input'
    lateinit var inemail: EditText
    lateinit var inpassword: EditText
    lateinit var insurname: EditText
    lateinit var inname: EditText
    lateinit var inpatronymic: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dev_sign_up)

        inemail = findViewById(R.id.email_input)
        inpassword = findViewById(R.id.password_input)
        insurname = findViewById(R.id.surname_input)
        inname = findViewById(R.id.name_input)
        inpatronymic = findViewById(R.id.patronymic_input)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }
    fun signup(view: View?) {
        if (removespaces(inemail.text.toString()) == "" ||
            removespaces(inpassword.text.toString()) == "" ||
            removespaces(insurname.text.toString()) == "" ||
            removespaces(inname.text.toString()) == "") {
            Toast.makeText(this, "You have an empty field", Toast.LENGTH_SHORT).show()
            return
        }
        if (checkForInternet(this).not()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
            return
        }
        /*
        viewModel.devregister(
            removespaces(inemail.text.toString()),
            removespaces(inpassword.text.toString()),
            fullname(insurname.text.toString(), inname.text.toString(), inpatronymic.text.toString()),
            readhash(this),
            0
        )

         */
        viewModel.myCResponse.observe(this, Observer{
                response ->
            val result = response.body()?.message ?: "No response. Please try again."
            val result_bool = response.body()?.error ?: true

            if (result_bool) {
                Toast.makeText(this, "ERROR: ".plus(result), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Registered New Developer Successfully", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            }
        })
    }
}