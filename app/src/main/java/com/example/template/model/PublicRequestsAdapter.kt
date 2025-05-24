package com.example.template.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.template.R
import com.example.template.functions.data_manipulation.globalAssignedIDs
import com.example.template.functions.data_manipulation.globalSubscribeID
import com.example.template.functions.data_manipulation.globalUnsubscribeID
import com.example.template.preferencesManager.AuthManager


class PublicRequestsAdapter(private val dataSet: MutableList<PublicRequest>) :
    RecyclerView.Adapter<PublicRequestsAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val address: TextView = view.findViewById(R.id.address)
        val upload: TextView = view.findViewById(R.id.upload)
        val deadline: TextView = view.findViewById(R.id.deadline)
        val description: TextView = view.findViewById(R.id.description)
        val subscribeToggleButton: Button = view.findViewById(R.id.admin_delete_button)
        val counter: TextView = view.findViewById(R.id.counter)
        var toggle: Boolean = false
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PublicRequestsAdapter.ViewHolder {

        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.public_request_item, viewGroup, false)
        return PublicRequestsAdapter.ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: PublicRequestsAdapter.ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val date = dataSet[position].date.split('T')[0].split('-')
        val deadline = dataSet[position].deadLine.split('T')[0].split('-')
        val id = dataSet[position].id
        viewHolder.address.text = dataSet[position].address
        viewHolder.upload.text = date[2] + '.' + date[1] + '.' + date[0]
        viewHolder.deadline.text = deadline[2] + '.' + deadline[1] + '.' + deadline[0]
        viewHolder.description.text = dataSet[position].description
        viewHolder.counter.text = dataSet[position].respondedPeople.toString().plus('/').plus(dataSet[position].neededPeopleNumber.toString())

        if (globalAssignedIDs.contains(id)) {
            viewHolder.toggle = true
            viewHolder.subscribeToggleButton.setText(R.string.unsubscribe)
        } else {
            viewHolder.toggle = false
            viewHolder.subscribeToggleButton.setText(R.string.subscribe)
        }

        viewHolder.subscribeToggleButton.setOnClickListener {
            viewHolder.toggle = !viewHolder.toggle
            // send request via changing some global value and warning the page thus

            // TODO: implement such request handling method
            if (viewHolder.toggle) { // if the user clicked 'subscribe'
                val numbers = viewHolder.counter.text.split('/')
                var firstNumber = numbers[0].toInt()
                ++firstNumber
                viewHolder.counter.text = firstNumber.toString().plus('/').plus(dataSet[position].neededPeopleNumber.toString())
                viewHolder.subscribeToggleButton.setText(R.string.unsubscribe)
                globalSubscribeID.value = dataSet[position].id
                globalUnsubscribeID.value = 0
            } else {
                val numbers = viewHolder.counter.text.split('/')
                var firstNumber = numbers[0].toInt()
                --firstNumber
                viewHolder.counter.text = firstNumber.toString().plus('/').plus(dataSet[position].neededPeopleNumber.toString())
                viewHolder.subscribeToggleButton.setText(R.string.subscribe)
                globalSubscribeID.value = 0
                globalUnsubscribeID.value = dataSet[position].id
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return dataSet.size
    }

}
