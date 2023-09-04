package com.juhnkim.lifecyclev3.activities

import FirestoreUtil
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.juhnkim.lifecyclev3.R
import java.util.Random

class FactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fact)
        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)

        val factText = findViewById<TextView>(R.id.random_fact_text)
        val newFactButton = findViewById<Button>(R.id.new_fact_button)

        val db = FirestoreUtil.getInstance();

        // Function to fetch and display a random fact
        fun fetchRandomFact() {
            db.collection("facts").get()
                .addOnSuccessListener { documents ->
                    val factList = ArrayList<String>()
                    for (document in documents) {
                        val fact = document.getString("fact") ?: ""
                        factList.add(fact)
                    }
                    // Display random fact
                    val randomIndex = Random().nextInt(factList.size)
                    factText.text = factList[randomIndex]
                }
                .addOnFailureListener { exception ->
                    Log.w("FactActivity", "Error fetching facts", exception)
                }
        }

        // Fetch the initial random fact
        fetchRandomFact()

        // Set up button to fetch a new random fact
        newFactButton.setOnClickListener {
            fetchRandomFact()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_login_activity -> {
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            R.id.action_register_activity -> {
                startActivity(Intent(this, RegisterActivity::class.java))
                true
            }
            R.id.action_second_activity -> {
                startActivity(Intent(this, SecondActivity::class.java))
                true
            }
            R.id.action_fact_activity -> {
                startActivity(Intent(this, FactActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}