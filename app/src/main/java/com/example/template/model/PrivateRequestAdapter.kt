package com.example.template.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.template.R
import com.example.template.functions.data_manipulation.globalDeleteRequestID
import com.example.template.functions.data_manipulation.globalMarkingRequest

class PrivateRequestsAdapter(private val dataSet: MutableList<PrivateRequest>) :
    RecyclerView.Adapter<PrivateRequestsAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.id)
        val address: TextView = view.findViewById(R.id.address)
        val upload: TextView = view.findViewById(R.id.upload)
        val deadline: TextView = view.findViewById(R.id.deadline)
        val description: TextView = view.findViewById(R.id.description)
        val deleteToggleButton: Button = view.findViewById(R.id.admin_delete_button)
        val markAsCompletedButton: Button = view.findViewById(R.id.admin_mark_as_completed_button)
        val counter: TextView = view.findViewById(R.id.counter)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PrivateRequestsAdapter.ViewHolder {

        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.private_request_item, viewGroup, false)
        return PrivateRequestsAdapter.ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: PrivateRequestsAdapter.ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.id.text = dataSet[position].id.toString()
        viewHolder.address.text = dataSet[position].address
        viewHolder.upload.text = dataSet[position].date
        viewHolder.deadline.text = dataSet[position].deadLine
        /*
        viewHolder.upload.text = dataSet[position].date.dayOfMonth.toString() + '.' + dataSet[position].date.monthValue.toString() + '.' + dataSet[position].date.year.toString()
        viewHolder.deadline.text = dataSet[position].deadLine.dayOfMonth.toString() + '.' + dataSet[position].deadLine.monthValue.toString() + '.' + dataSet[position].deadLine.year.toString()
         */
        viewHolder.description.text = dataSet[position].description
        viewHolder.counter.text = dataSet[position].respondedPeople.count().toString().plus('/').plus(dataSet[position].neededPeopleNumber.toString())

        if (dataSet[position].isComplited) {
            viewHolder.counter.setText(R.string.completed)
            viewHolder.markAsCompletedButton.isEnabled = false
        }


        viewHolder.deleteToggleButton.setOnClickListener {
            // send request via changing some global value and warning the page thus to change the layout
            globalDeleteRequestID.value = dataSet[position].id
            globalMarkingRequest.value = PrivateRequest(0, 0, "", "", "", 0, mutableListOf<Int>(), 0, "", false, false, "")
        }
        viewHolder.markAsCompletedButton.setOnClickListener {
            // send request via changing some global value and warning the page thus to change the layout
            globalMarkingRequest.value = dataSet[position]
            globalDeleteRequestID.value = 0
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return dataSet.size
    }

}
