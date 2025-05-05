package com.example.template

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.template.functions.checkForInternet
import com.example.template.functions.data_manipulation.globalChangeUser
import com.example.template.functions.data_manipulation.globalDeleteUser
import com.example.template.model.UsersAdapter
import com.example.template.repository.Repository
import com.example.template.viewModel.MainViewModel
import com.example.template.viewModelFactory.MainViewModelFactory

class GetUsers : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_users)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        recyclerView = findViewById(R.id.recyclerViewUsers)
        usersAdapter = UsersAdapter(viewModel.myResponseUsers)
        recyclerView.adapter = usersAdapter
    }
    fun refresh(view: View?) {
        if (checkForInternet(this).not()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
            return
        }

        viewModel.myResponseUsers.clear()
        viewModel.getUsers()
        recyclerView.adapter?.notifyDataSetChanged()

        viewModel.myString.observe(this, Observer {
            response ->
            if (response == "GOTUSERS") {
                viewModel.myString.value = ""
                recyclerView.swapAdapter(UsersAdapter(viewModel.myResponseUsers), true)
                recyclerView.layoutManager = LinearLayoutManager(this)
            }
        })

        globalChangeUser.observe(this, Observer {
            val intent = Intent(this, ChangeUser::class.java)
            startActivity(intent)
        })

        globalDeleteUser.observe(this, Observer {
            val intent = Intent(this, DeleteUser::class.java)
            startActivity(intent)
        })
    }
}