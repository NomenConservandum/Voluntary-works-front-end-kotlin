package com.example.template

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.template.functions.data_manipulation.globalMarkingRequest
import com.example.template.repository.Repository
import com.example.template.viewModel.MainViewModel
import com.example.template.viewModelFactory.MainViewModelFactory

class MarkAsCompletedPage : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    lateinit var peopleList: ListView
    lateinit var addRemoveButton: Button
    lateinit var sendButton: Button
    lateinit var IDtoAddorRemove: EditText
    lateinit var requestID: EditText
    lateinit var peopleIdsList: List<Int>
    lateinit var adapter: ArrayAdapter<Int?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mark_as_completed_page)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        peopleList = findViewById(R.id.people_list)
        addRemoveButton = findViewById(R.id.add_remove_person_button)
        sendButton = findViewById(R.id.mark_as_completed_button)
        IDtoAddorRemove = findViewById(R.id.id_to_add_or_remove)
        requestID = findViewById(R.id.request_id)
        requestID.setText(globalMarkingRequest.value!!.id.toString())
        peopleIdsList = globalMarkingRequest.value!!.respondedPeople

        adapter = ArrayAdapter<Int?>(
            this@MarkAsCompletedPage,
            android.R.layout.simple_list_item_1,
            peopleIdsList as List<Int?>
        )

        peopleList.adapter = adapter
    }
    fun addRemove(view: View?) {
        val userID = IDtoAddorRemove.text.toString().toInt()
        if (userID >= 1) {
            if (!peopleIdsList.contains(userID))
                peopleIdsList = peopleIdsList.plus(userID)
            else
                peopleIdsList = peopleIdsList.minus(userID)
            adapter.notifyDataSetChanged()
        }
    }
    fun send(view: View?) {
        viewModel.markAsCompleted(requestID.text.toString().toInt(), peopleIdsList)
        //Toast.makeText(this, peopleIdsList.toString(), Toast.LENGTH_LONG).show()
        viewModel.myString.observe(this, Observer {
            response ->
            if (response == "MARKED_AS_COMPLETED_SUCCESSFULLY")
                Toast.makeText(this, "Request " + requestID.text.toString() + " was marked completed", Toast.LENGTH_LONG).show()
        })
        viewModel.myErrorCodeResponse.observe(this, Observer {
            response ->
            if (response != 401) {
                Toast.makeText(this, "ERROR: " + response.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
}