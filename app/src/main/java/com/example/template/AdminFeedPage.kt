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
import com.example.template.functions.data_manipulation.globalDeleteRequest
import com.example.template.functions.data_manipulation.globalSubscribeID
import com.example.template.functions.data_manipulation.globalUnsubscribeID
import com.example.template.functions.navigation.navigationhub
import com.example.template.functions.navigation.tomyprofilepage
import com.example.template.model.PrivateRequestsAdapter
import com.example.template.model.PublicRequestsAdapter
import com.example.template.repository.Repository
import com.example.template.viewModel.MainViewModel
import com.example.template.viewModelFactory.MainViewModelFactory

class AdminFeedPage : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var privateRequestAdapter: PrivateRequestsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_feed_page)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        recyclerView = findViewById(R.id.recyclerViewPrivateRequests)
        privateRequestAdapter = PrivateRequestsAdapter(viewModel.myResponsePrivateRequests)
        recyclerView.adapter = privateRequestAdapter
    }

    fun refresh(view: View?) {
        if (checkForInternet(this).not()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
            return
        }

        viewModel.myResponsePrivateRequests.clear()

        viewModel.getPrivateRequests()
        recyclerView.adapter?.notifyDataSetChanged()

        viewModel.myString.observe(this, Observer {
                response ->
            if (response == "GOT_REQUESTS") {
                viewModel.myString.value = ""
                recyclerView.swapAdapter(PrivateRequestsAdapter(viewModel.myResponsePrivateRequests), true)
                recyclerView.layoutManager = LinearLayoutManager(this)
            }
        })

        globalDeleteRequest.observe(this, Observer {
            //   request ->
            // switch to the 'delete request' page
        })

    }
    fun toMyProfilePage(view: View?) {
        tomyprofilepage(this)
    }

    fun goback(view: View?) {
        navigationhub(this, "Admin")
        this.finish()
    }
}