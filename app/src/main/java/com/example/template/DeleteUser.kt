package com.example.template

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.template.functions.checkForInternet
import com.example.template.functions.data_manipulation.globalChangeUser
import com.example.template.functions.data_manipulation.globalDeleteUser
import com.example.template.functions.navigation.navigationhub
import com.example.template.functions.navigation.tosignuppage
import com.example.template.functions.removespaces
import com.example.template.model.User
import com.example.template.repository.Repository
import com.example.template.viewModel.MainViewModel
import com.example.template.viewModelFactory.MainViewModelFactory

class DeleteUser : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var inid: EditText

    var toDelete = -1 // Id of the user we will remove
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_user)

        toDelete = globalDeleteUser.value?.id ?: -1
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        inid = findViewById(R.id.delete_id)
        inid.setText(toDelete.toString())
    }

    fun delete(view: View?) {
        if (removespaces(inid.text.toString()) == "") {
            Toast.makeText(this, "You have an empty field", Toast.LENGTH_SHORT).show()
            return
        }
        if (checkForInternet(this).not()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
            return
        }

        viewModel.delete(inid.text.toString().toInt())

        // observe
        viewModel.myString.observe(this, Observer {
                response ->
            if (response == "DELETED_SUCCESSFULLY") {
                navigationhub(this, "CRUD MENU")
                this.finish()
                Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.myErrorCodeResponse.observe(this, Observer {
                response ->
            if (response == 422) {
                Toast.makeText(this, "ERROR: invalid input", Toast.LENGTH_SHORT).show()
            } else if (response != 204) {
                Toast.makeText(this, "ERROR: ".plus(response.toString()), Toast.LENGTH_SHORT).show()
                if (response == null) {
                    Toast.makeText(this, "No Response", Toast.LENGTH_SHORT).show()
                }
            }
            this.finish()
        })
    }
}