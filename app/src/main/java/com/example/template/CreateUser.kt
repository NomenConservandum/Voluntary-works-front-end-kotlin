package com.example.template

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.template.functions.checkForInternet
import com.example.template.functions.removespaces
import com.example.template.repository.Repository
import com.example.template.viewModel.MainViewModel
import com.example.template.viewModelFactory.MainViewModelFactory

class CreateUser : AppCompatActivity() {
	private lateinit var viewModel: MainViewModel

	// 'in' prefix for 'input'
	lateinit var inEmail: EditText
	lateinit var inPassword: EditText
	lateinit var inFirstname: EditText
	lateinit var inSecondname: EditText
	lateinit var inPatronymic: EditText
	lateinit var inTelegramURL: EditText

	override fun onCreate(savedInstanceState: Bundle?) {
		val repository = Repository()
		val viewModelFactory = MainViewModelFactory(repository)
		viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

		super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

		inEmail = findViewById(R.id.create_email)
		inPassword = findViewById(R.id.create_password)
		inFirstname = findViewById(R.id.create_name)
		inSecondname = findViewById(R.id.create_surname)
		inPatronymic = findViewById(R.id.create_patronymic)
		inTelegramURL = findViewById(R.id.create_telegram)
    }
	fun create(view: View?) {
		if (removespaces(inEmail.text.toString()) == "" ||
			removespaces(inPassword.text.toString()) == "" ||
			removespaces(inFirstname.text.toString()) == "" ||
			removespaces(inPatronymic.text.toString()) == "" ||
			removespaces(inTelegramURL.text.toString()) == "") {
			Toast.makeText(this, "You have an empty field", Toast.LENGTH_SHORT).show()
			return
		}
		if (checkForInternet(this).not()) {
			Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
			return
		}

		// server API request
		viewModel.create(
			removespaces(inEmail.text.toString()),
			removespaces(inPassword.text.toString()),
			removespaces(inFirstname.text.toString()),
			removespaces(inSecondname.text.toString()),
			removespaces(inPatronymic.text.toString()),
			removespaces(inTelegramURL.text.toString()),
		)
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
			if (value == "SUCCESS")
				Toast.makeText(this, "The user has been successfully created", Toast.LENGTH_SHORT).show()
		})
	}
}