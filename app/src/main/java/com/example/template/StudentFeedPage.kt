package com.example.template

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.template.functions.checkForInternet
import com.example.template.functions.data_manipulation.globalSubscribeID
import com.example.template.functions.data_manipulation.globalUnsubscribeID
import com.example.template.functions.data_manipulation.logout
import com.example.template.functions.navigation.tomyprofilepage
import com.example.template.model.PublicRequestsAdapter
import com.example.template.repository.Repository
import com.example.template.viewModel.MainViewModel
import com.example.template.viewModelFactory.MainViewModelFactory

class StudentFeedPage : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var publicRequestAdapter: PublicRequestsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_feed_page)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        recyclerView = findViewById(R.id.recyclerViewPrivateRequests)
        publicRequestAdapter = PublicRequestsAdapter(viewModel.myResponsePublicRequests)
        recyclerView.adapter = publicRequestAdapter
    }

    fun refresh(view: View?) {
        if (checkForInternet(this).not()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
            return
        }

        viewModel.myResponsePublicRequests.clear()
        /*
        viewModel.myResponsePublicRequests.addAll(0, mutableListOf<PublicRequest>(
            PublicRequest(0, 0, "", "", "", 0, 0, 10, "nothing here yet", false, false),
            PublicRequest(1, 0, "yay", "", "", 20, 3, 4, "VERY PROFITABLE TASK", false, false),
        )) // temporary
         */

        viewModel.getPublicRequests()
        //viewModel.myString.value = "GOT_REQUESTS" // temporary
        recyclerView.adapter?.notifyDataSetChanged()

        viewModel.myString.observe(this, Observer {
                response ->
            if (response == "GOT_REQUESTS") {
                viewModel.myString.value = ""
                recyclerView.swapAdapter(PublicRequestsAdapter(viewModel.myResponsePublicRequests), true)
                recyclerView.layoutManager = LinearLayoutManager(this)
            }
        })

        globalSubscribeID.observe(this, Observer {
            id ->
            if (id != 0) viewModel.assignMe(id)
        })

        globalUnsubscribeID.observe(this, Observer {
            id ->
            if (id != 0) viewModel.unassignMe(id)
        })

    }
    fun toMyProfilePage(view: View?) {
        tomyprofilepage(this)
    }

    fun logout(view: View?) {
        logout(this)
        this.finish()
    }
}