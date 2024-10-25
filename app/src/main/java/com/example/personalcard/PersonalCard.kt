package com.example.personalcard

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PersonalCard : AppCompatActivity() {
    private lateinit var toolbarPersonalCard: Toolbar
    private var photoUri: Uri? = null

    private lateinit var personalPhotoIV: ImageView

    private lateinit var nameTV: TextView
    private lateinit var secondNameTV: TextView
    private lateinit var dateOfBirthTV: TextView
    private lateinit var ageTV: TextView
    private lateinit var daysUntilBirthTV: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_personal_card)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbarPersonalCard = findViewById(R.id.toolbarPersonalCard)
        setSupportActionBar(toolbarPersonalCard)
        title = "Персональная информация"

        personalPhotoIV = findViewById(R.id.personalPhotoIV)
        nameTV = findViewById(R.id.nameTV)
        secondNameTV = findViewById(R.id.secondNameTV)
        dateOfBirthTV = findViewById(R.id.dateOfBirthTV)
        ageTV = findViewById(R.id.ageTV)
        daysUntilBirthTV = findViewById(R.id.daysUntilBirthTV)

        photoUri = intent.getStringExtra("photoUri").let { Uri.parse(it) }
        personalPhotoIV.setImageURI(photoUri)
        nameTV.text = intent.getStringExtra("name")
        secondNameTV.text = intent.getStringExtra("secondName")
        val dateOfBirth = intent.getStringExtra("dateOfBirth")
        dateOfBirthTV.text = dateOfBirth
        val date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(dateOfBirth!!)
        val age = calculateAge(date!!)
        val daysUntil = daysUntilBirthDay(date)
        ageTV.text = age.toString()
        daysUntilBirthTV.text = daysUntil.toString()

    }

    private fun calculateAge(dateOfBirth: Date): Int {
        val today = Calendar.getInstance()
        val birthDate = Calendar.getInstance()
        birthDate.time = dateOfBirth
        var age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        return age
    }

    private fun daysUntilBirthDay(dateOfBirth: Date): Int {
        val today = Calendar.getInstance()
        val birthDate = Calendar.getInstance()
        birthDate.time = dateOfBirth
        birthDate.set(Calendar.YEAR, today.get(Calendar.YEAR))

        if (today.after(birthDate)) {
            birthDate.set(Calendar.YEAR, today.get(Calendar.YEAR) + 1)
        }
        val daysUntilBirthDay = ((birthDate.timeInMillis - today.timeInMillis) / (1000 * 60 * 60 * 24)).toInt()
        return daysUntilBirthDay
    }
}