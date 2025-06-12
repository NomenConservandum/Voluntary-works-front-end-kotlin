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
import com.example.template.functions.data_manipulation.globalAssignedIDs
import com.example.template.functions.data_manipulation.globalSubscribeID
import com.example.template.functions.data_manipulation.globalUnsubscribeID
import com.example.template.functions.data_manipulation.logout
import com.example.template.functions.navigation.tomyprofilepage
import com.example.template.model.PublicRequestsAdapter
import com.example.template.preferencesManager.AuthManager
import com.example.template.repository.Repository
import com.example.template.viewModel.MainViewModel
import com.example.template.viewModelFactory.MainViewModelFactory

class StudentFeedPage : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var publicRequestAdapter: PublicRequestsAdapter
    val authman = AuthManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        authman.readAssignitions(this) // write the list globally
        Toast.makeText(this, globalAssignedIDs.toString(), Toast.LENGTH_LONG).show()

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

        viewModel.getPublicRequests()
        recyclerView.adapter?.notifyDataSetChanged()

        viewModel.myString.observe(this, Observer {
                response ->
            if (response == "GOT_REQUESTS") {
                viewModel.myString.value = ""
                recyclerView.swapAdapter(PublicRequestsAdapter(viewModel.myResponsePublicRequests), true)
                recyclerView.layoutManager = LinearLayoutManager(this)
            }
        })

        var thinkSubscribe = false
        globalSubscribeID.observe(this, Observer {
            id ->
            if (id != 0 && !thinkSubscribe) {
                thinkSubscribe = true
                viewModel.assignMe(id)
                Toast.makeText(this, "assigning to " + id.toString() + "...", Toast.LENGTH_LONG).show()
            } else if (thinkSubscribe) {
                Toast.makeText(this, "Please wait until the request is completed", Toast.LENGTH_LONG).show()
            }
        })
        var thinkUnsubscribe = false
        globalUnsubscribeID.observe(this, Observer {
            id ->
            if (id != 0 && !thinkUnsubscribe) {
                thinkUnsubscribe = true
                viewModel.unassignMe(id)
                Toast.makeText(this, "unassigning from " + id.toString() + "...", Toast.LENGTH_LONG).show()
            } else if (thinkUnsubscribe) {
                Toast.makeText(this, "Please wait until the request is completed", Toast.LENGTH_LONG).show()
            }
        })
        viewModel.myString.observe(this, Observer {
            response ->
            if (response == "ASSIGNED_SUCCESSFULLY") {
                Toast.makeText(this, "assigned to the post successfully", Toast.LENGTH_LONG).show()
                // save it into the preferences
                authman.addAssignition(globalSubscribeID.value?: 0, this)
            } else if (response == "UNASSIGNED_SUCCESSFULLY") {
                Toast.makeText(this, "unassigned from the post successfully", Toast.LENGTH_LONG).show()
                // remove it from the preferences
                authman.removeAssignition(globalUnsubscribeID.value?: 0, this)
            }
            thinkUnsubscribe = false
            thinkSubscribe = false
        })
        viewModel.myErrorCodeResponse.observe(this, Observer {
            code ->
            Toast.makeText(this, "failed to (un)assign: " + code.toString(), Toast.LENGTH_LONG).show()
            thinkUnsubscribe = false
            thinkSubscribe = false
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