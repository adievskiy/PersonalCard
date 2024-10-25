package com.example.personalcard

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val GALLERY_REQUEST = 100
    var photoUri: Uri? = null

    private lateinit var toolbar: Toolbar

    private lateinit var photoIV: ImageView

    private lateinit var nameET: EditText
    private lateinit var secondNameET: EditText
    private lateinit var dateOfBirthET: EditText

    private lateinit var saveBTN: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbar)
        title = "Персональные данные"

        photoIV = findViewById(R.id.photoIV)
        nameET = findViewById(R.id.nameET)
        secondNameET = findViewById(R.id.secondNameET)
        dateOfBirthET = findViewById(R.id.dateOfBirthET)


        saveBTN = findViewById(R.id.saveBTN)

        photoIV.setOnClickListener {
            val photoPickerIntent =Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        dateOfBirthET.setOnClickListener {
            showDateDialog()
        }

        saveBTN.setOnClickListener {
            saveButtonActivity()
        }
    }

    private fun saveButtonActivity() {
        val name = nameET.text.toString()
        val secondName = secondNameET.text.toString()
        val dateOfBirth = dateOfBirthET.text.toString()
        val intent = Intent(this, PersonalCard::class.java)
        intent.putExtra("photoUri", photoUri.toString())
        intent.putExtra("name", name)
        intent.putExtra("secondName", secondName)
        intent.putExtra("dateOfBirth", dateOfBirth)
        startActivity(intent)
    }

    @SuppressLint("DefaultLocale")
    private fun showDateDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateDialog = DatePickerDialog(this, {_, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = String.format("%02d.%02d.%d", selectedDay, selectedMonth + 1, selectedYear)
            dateOfBirthET.setText(selectedDate)
        }, year, month, day)
        dateDialog.show()
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            photoUri = data?.data
            photoIV.setImageURI(photoUri)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.exitMenuItem -> finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }
}