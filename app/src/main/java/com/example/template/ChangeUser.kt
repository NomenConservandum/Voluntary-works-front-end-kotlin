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

    lateinit var inEmail: EditText
    lateinit var inPassword: EditText
    lateinit var inName: EditText
    lateinit var inTGUrl: EditText
    lateinit var inRole: EditText
    lateinit var inGroup: EditText
    lateinit var inPoints: EditText
    lateinit var inCompleted: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_user)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        inEmail = findViewById(R.id.change_email)
        inPassword = findViewById(R.id.change_password)
        inName = findViewById(R.id.change_name)
        inTGUrl = findViewById(R.id.change_tgurl)
        inRole = findViewById(R.id.change_role)
        inGroup = findViewById(R.id.change_group)
        inPoints = findViewById(R.id.change_points)
        inCompleted = findViewById(R.id.change_completed)

        toChange = globalChangeUser.value ?: User(0, "", "", "", "", "", "", 0, 0)
        oldEmail = globalChangeUser.value?.email ?: ""

        inEmail.setText(globalChangeUser.value?.email)
        inPassword.setText("")
        inName.setText(globalChangeUser.value?.name)
        inTGUrl.setText(globalChangeUser.value?.telegramUrl)
        inRole.setText(globalChangeUser.value?.role)
        inGroup.setText(globalChangeUser.value?.group)
        inPoints.setText(globalChangeUser.value?.points?.toString())
        inCompleted.setText(globalChangeUser.value?.finishedRequests?.toString())
    }

    fun change(view: View?) {
        if (removespaces(inEmail.text.toString()) == "" ||
            removespaces(inPassword.text.toString()) == "" ||
            removespaces(inName.text.toString()) == "") {
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
                inEmail.text.toString(),
                inPassword.text.toString(),
                inName.text.toString(),
                inTGUrl.text.toString(),
                inRole.text.toString(),
                inGroup.text.toString(),
                inPoints.text.toString().toInt(),
                inCompleted.text.toString().toInt()
            ),
            oldEmail
        )

        // observe
        viewModel.myString.observe(this, Observer {
                response ->
            if (response == "SUCCESS") {
                navigationhub(this, "Admin")
                this.finish()
                Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.myErrorCodeResponse.observe(this, Observer {
                response ->
            if (response == 422) {
                Toast.makeText(this, "ERROR: invalid input", Toast.LENGTH_SHORT).show()
            } else if (response != 201) {
                Toast.makeText(this, "ERROR: ".plus(response.toString()), Toast.LENGTH_SHORT).show()
                if (response == null) {
                    Toast.makeText(this, "No Response", Toast.LENGTH_SHORT).show()
                }
            }
            this.finish()
        })
    }
}