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
import com.example.template.functions.data_manipulation.globalDeleteRequestID
import com.example.template.functions.data_manipulation.globalMarkingRequest
import com.example.template.functions.navigation.navigationhub
import com.example.template.functions.navigation.tomyprofilepage
import com.example.template.model.PrivateRequest
import com.example.template.model.PrivateRequestsAdapter
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
                /*
                var lst: MutableList<PrivateRequest> = mutableListOf()
                for (item in viewModel.myResponsePrivateRequests) {
                    if (item.isComplited) continue
                    lst = lst.plus(item)
                }
                 */
                recyclerView.swapAdapter(PrivateRequestsAdapter(viewModel.myResponsePrivateRequests), true)
                recyclerView.layoutManager = LinearLayoutManager(this)
            } else if (response == "DELETED_REQUEST_SUCCESSFULLY") {
                Toast.makeText(this, "Deleted the request successfully", Toast.LENGTH_LONG).show()
            }
        })

        globalDeleteRequestID.observe(this, Observer {
               id ->
            if (id >= 1) {
                viewModel.deleteRequest(id)
                Toast.makeText(this, "Deleting the request " + id.toString(), Toast.LENGTH_LONG).show()
            }
        })

        globalMarkingRequest.observe(this, Observer {
                request ->
            if (request.id >= 1) {
                val intent = Intent(this, MarkAsCompletedPage::class.java)
                this.startActivity(intent)
            }
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