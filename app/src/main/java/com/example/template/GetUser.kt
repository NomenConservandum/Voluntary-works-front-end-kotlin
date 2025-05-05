package com.example.template

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.template.functions.checkForInternet
import com.example.template.functions.removespaces
import com.example.template.repository.Repository
import com.example.template.viewModel.MainViewModel
import com.example.template.viewModelFactory.MainViewModelFactory

class GetUser : AppCompatActivity() {
	private lateinit var viewModel: MainViewModel

	// 'in' prefix for 'input'
	lateinit var inemail: EditText
	lateinit var inid: EditText

	lateinit var outputText: TextView
	lateinit var idtoemailSwitch: Switch
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_user)


		val repository = Repository()
		val viewModelFactory = MainViewModelFactory(repository)

		viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

		inemail = findViewById(R.id.findByEmailText)
		inid = findViewById(R.id.findByIdText)

		outputText = findViewById(R.id.GetUserOutputText)
		idtoemailSwitch = findViewById(R.id.idtoemailSwitch)
    }

	fun get(view: View?) {
		if (removespaces(inemail.text.toString()) == "" &&
			removespaces(inid.text.toString()) == "") {
			Toast.makeText(this, "You have an empty field", Toast.LENGTH_SHORT).show()
			return
		}
		if (checkForInternet(this).not()) {
			Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
			return
		}
		Log.i("EMAIL", inemail.text.toString())

		if (idtoemailSwitch.isChecked) {
			Log.i("request", inemail.text.toString())
			viewModel.getUserByEmail(inemail.text.toString())
		} else {
			Log.i("request", inid.text.toString())
			viewModel.getUserById(inid.text.toString().toInt())
		}
		viewModel.myErrorCodeResponse.observe(this, Observer {
				code ->
			if (code != 200) {
				Toast.makeText(this, "ERROR: ".plus(code.toString()), Toast.LENGTH_SHORT).show()
				if (code == null)
					Toast.makeText(this, "No Response", Toast.LENGTH_SHORT).show()
			}
		})
		viewModel.myUserResponse.observe(this, Observer {
				value ->
			outputText.text = value.toString()
		})

	}

}