package com.juhnkim.lifecyclev3.activities

import FirestoreUtil
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.juhnkim.lifecyclev3.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login_button)
        val registerButton = findViewById<Button>(R.id.register_button)
        val db = FirestoreUtil.getInstance();

        loginButton.setOnClickListener {
            val enteredUsername = username.text.toString()
            val enteredPassword = password.text.toString()

            // Query Firestore to find a user with the entered username
            db.collection("users")
                .whereEqualTo("username", enteredUsername)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val storedPassword = document.getString("password") ?: ""

                        if (enteredPassword == storedPassword) {
                            // On successful login, navigate to SecondActivity
                            val intent = Intent(this, SecondActivity::class.java)
                            startActivity(intent)
                            return@addOnSuccessListener
                        }
                    }
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                }
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
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