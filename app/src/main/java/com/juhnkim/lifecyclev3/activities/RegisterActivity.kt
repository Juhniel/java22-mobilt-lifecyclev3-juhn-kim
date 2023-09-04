package com.juhnkim.lifecyclev3.activities

import FirestoreUtil
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.juhnkim.lifecyclev3.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)

        val firstName = findViewById<EditText>(R.id.first_name)
        val lastName = findViewById<EditText>(R.id.last_name)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val registerButton = findViewById<Button>(R.id.register_button)

        registerButton.setOnClickListener {
            val db = FirestoreUtil.getInstance()

            val user = hashMapOf(
                "firstName" to firstName.text.toString(),
                "lastName" to lastName.text.toString(),
                "username" to username.text.toString(),
                "password" to password.text.toString()
            )

            val usernameStr = username.text.toString()
            db.collection("users")
                .document(usernameStr)
                .set(user)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error writing document", e)
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        super.onPrepareOptionsMenu(menu)
        val menuItem = menu.findItem(R.id.action_second_activity)
        menuItem.isVisible = false
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