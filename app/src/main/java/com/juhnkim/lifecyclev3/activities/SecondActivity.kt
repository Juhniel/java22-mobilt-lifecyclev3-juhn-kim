package com.juhnkim.lifecyclev3.activities

import FirestoreUtil
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.juhnkim.lifecyclev3.R


class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)

        val nameInput = findViewById<EditText>(R.id.name_input)
        val factInput = findViewById<EditText>(R.id.fact_input)
        val languageGroup = findViewById<RadioGroup>(R.id.language_group)
        val studentCheckBox = findViewById<CheckBox>(R.id.student_checkbox)
        val submitButton = findViewById<Button>(R.id.submit_button)


        // Submit fact button
        submitButton.setOnClickListener {
            val db = FirestoreUtil.getInstance()

            // Gather data
            val name = nameInput.text.toString()
            val fact = factInput.text.toString()
            val isStudent = studentCheckBox.isChecked
            val selectedLanguageId = languageGroup.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(selectedLanguageId)
            val language = radioButton?.text.toString() ?: ""

            // Create a new user object
            val userInfo = hashMapOf(
                "name" to name,
                "fact" to fact,
                "isStudent" to isStudent,
                "language" to language
            )

            db.collection("facts")
                .add(userInfo)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        "SecondActivity",
                        "DocumentSnapshot added with ID: ${documentReference.id}"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w("SecondActivity", "Error adding document", e)
                }

            val intent = Intent(this, FactActivity::class.java)
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


            R.id.action_fact_activity -> {
                startActivity(Intent(this, FactActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}