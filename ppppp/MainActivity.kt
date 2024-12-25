package com.example.ppppp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private val roles = listOf(
        "Выберите должность",
        "Инженер",
        "Конструктор",
        "Проектировщик",
        "Мастер",
        "Энергетик",
        "Механик"
    )
    private lateinit var firstNameET: EditText
    private lateinit var lastNameET: EditText
    private lateinit var roleSpinner: Spinner
    private lateinit var listView: ListView
    private lateinit var saveBTN: Button
    private lateinit var personAdapter: PersonAdapter
    private val personList = mutableListOf<Person>()
    private lateinit var ageET: EditText


    private val filteredPersonList = mutableListOf<Person>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        firstNameET = findViewById(R.id.firstNameET)
        lastNameET = findViewById(R.id.lastNameET)
        roleSpinner = findViewById(R.id.spinner)
        listView = findViewById(R.id.listView)
        saveBTN = findViewById(R.id.saveBTN)
        ageET = findViewById(R.id.ageET)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        personAdapter = PersonAdapter(this, filteredPersonList)
        listView.adapter = personAdapter

        val roleAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roleSpinner.adapter = roleAdapter

        roleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filterList(roles[position]) // Фильтруем список при выборе роли
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        saveBTN.setOnClickListener {
            addPerson()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            deletePerson(position)
        }
    }

    private fun addPerson() {
        val firstname = firstNameET.text.toString()
        val lastname = lastNameET.text.toString()
        val age = ageET.text.toString()
        val role = roleSpinner.selectedItem.toString()

        if (firstname.isEmpty() || lastname.isEmpty() || age.isEmpty() || role == "Выберите должность") {
            Toast.makeText(this, "Все поля должны быть заполнены.", Toast.LENGTH_SHORT).show()
        } else {
            val person = Person(firstname, lastname, age.toInt(), role)
            personList.add(person)
            filterList(role)
            clearInputFields()
        }
    }


    private fun filterList(selectedRole: String) {
        filteredPersonList.clear()
        if (selectedRole == "Выберите должность") {
            filteredPersonList.addAll(personList)
        } else {
            filteredPersonList.addAll(personList.filter { it.role == selectedRole })
        }
        personAdapter.notifyDataSetChanged()
    }

    private fun deletePerson(position: Int) {
        if (position < filteredPersonList.size) {
            val personToDelete = filteredPersonList[position]
            personList.remove(personToDelete)
            filterList(roleSpinner.selectedItem.toString())
            Toast.makeText(this, "Работник удален", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearInputFields() {
        firstNameET.text.clear()
        lastNameET.text.clear()
        ageET.text.clear()
        roleSpinner.setSelection(0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}