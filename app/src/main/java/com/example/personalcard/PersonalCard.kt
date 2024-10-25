package com.example.personalcard

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PersonalCard : AppCompatActivity() {
    private lateinit var toolbarPersonalCard: Toolbar
    private var photoUri: Uri? = null

    private lateinit var personalPhotoIV: ImageView

    private lateinit var nameTV: TextView
    private lateinit var secondNameTV: TextView
    private lateinit var dateOfBirthTV: TextView

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

        photoUri = intent.getStringExtra("photoUri").let { Uri.parse(it) }
        personalPhotoIV.setImageURI(photoUri)
        nameTV.text = intent.getStringExtra("name")
        secondNameTV.text = intent.getStringExtra("secondName")
        dateOfBirthTV.text = intent.getStringExtra("dateOfBirth")

    }
}