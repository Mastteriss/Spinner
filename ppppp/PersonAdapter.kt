package com.example.ppppp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PersonAdapter(
    context: Context,
    private val persons: List<Person>
) : ArrayAdapter<Person>(context, 0, persons) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val person = getItem(position)


        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)


        val firstNameTextView = view.findViewById<TextView>(android.R.id.text1)
        val roleTextView = view.findViewById<TextView>(android.R.id.text2)


        firstNameTextView.text = "${person?.firstname} ${person?.lastname}"
        roleTextView.text = person?.role

        return view
    }
}