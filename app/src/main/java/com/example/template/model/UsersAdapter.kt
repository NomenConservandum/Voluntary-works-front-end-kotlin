package com.example.template.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.template.R
import com.example.template.functions.data_manipulation.globalChangeUser
import com.example.template.functions.data_manipulation.globalDeleteUser


class UsersAdapter(private val dataSet: MutableList<User>) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.id)
        val email: TextView = view.findViewById(R.id.email)
        val firstname: TextView = view.findViewById(R.id.firstname)
        val secondname: TextView = view.findViewById(R.id.secondname)
        val changeButton: Button = view.findViewById(R.id.change)
        val deleteButton: Button = view.findViewById(R.id.delete)
        init {
            // Define click listener for the ViewHolder's View
            /*
            id = view.findViewById(R.id.id)
            email = view.findViewById(R.id.email)
            fullname = view.findViewById(R.id.fullname)
            role = view.findViewById(R.id.role)
            organization_id = view.findViewById(R.id.organization_id)
            done = view.findViewById(R.id.done)
            score = view.findViewById(R.id.score)
            current = view.findViewById(R.id.current)

             */
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.full_user_item, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        // What a mess 😭
        // to put it simply, it just joints the "<field>: " string and it's value as a string
        /*
        viewHolder.id.text = viewHolder.id.text.toString().plus(dataSet[position].id.toString())
        viewHolder.email.text = viewHolder.email.text.toString().plus(dataSet[position].email)
        viewHolder.fullname.text = viewHolder.fullname.text.toString().plus(dataSet[position].fullname)
        viewHolder.role.text = viewHolder.role.text.toString().plus(dataSet[position].role)
        viewHolder.organization_id.text = viewHolder.organization_id.text.toString().plus(dataSet[position].organization_id.toString())
        viewHolder.done.text = viewHolder.done.text.toString().plus(dataSet[position].done.toString())
        viewHolder.score.text = viewHolder.score.text.toString().plus(dataSet[position].score.toString())
        viewHolder.current.text = viewHolder.current.text.toString().plus(dataSet[position].current)

         */
        viewHolder.id.text = viewHolder.id.text.toString().plus(dataSet[position].id)
        viewHolder.email.text = viewHolder.email.text.toString().plus(dataSet[position].email)
        viewHolder.firstname.text = viewHolder.firstname.text.toString().plus(dataSet[position].firstName)
        viewHolder.secondname.text = viewHolder.secondname.text.toString().plus(dataSet[position].secondName)

        viewHolder.changeButton.setOnClickListener {
            globalChangeUser.value = dataSet[position] // as the value is changed,
            // the page is notified and we're switching to the 'change' page
        }

        viewHolder.deleteButton.setOnClickListener {
            globalDeleteUser.value = dataSet[position] // as the value is changed,
            // the page is notified and we're switching to the 'delete' page.
        }

        //viewHolder.delete = view.findViewById<Button>(R.id.delete)
        /*
        viewHolder.delete.setOnClickListener {
            deletionrequesteduser = Users(
                id = dataSet[position].id,
                email = dataSet[position].email,
                password_hash = dataSet[position].password_hash,
                fullname = dataSet[position].fullname,
                role = dataSet[position].role,
                organization_id = dataSet[position].organization_id,
                done = dataSet[position].done,
                score = dataSet[position].score,
                current = dataSet[position].current
            )

       viewHolder.viewModel.delete(Users(
           id = dataSet[position].id,
           email = dataSet[position].email,
           password_hash = dataSet[position].password_hash,
           fullname = dataSet[position].fullname,
           role = dataSet[position].role,
           organization_id = dataSet[position].organization_id,
           done = dataSet[position].done,
           score = dataSet[position].score,
           current = dataSet[position].current
       ))

            //Log.i("GlobalDataUpdate", "requesteduser changed successfully")
        }

         */
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return dataSet.size
    }

}
