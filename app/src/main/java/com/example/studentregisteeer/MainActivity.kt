package com.example.studentregisteeer

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.studentregisteeer.db.Student
import com.example.studentregisteeer.db.StudentDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var etName:EditText
    private lateinit var etEmail:EditText
    private lateinit var btCreate:Button
    private lateinit var btClear:Button
    private lateinit var viewModel: StudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etEmail = findViewById(R.id.etEmail)
        etName = findViewById(R.id.etName)
        btCreate = findViewById(R.id.btCreate)
        btClear = findViewById(R.id.btClear)

        val dao = StudentDatabase.getInstance(application).studentDao()
        val factory = StudentViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory)[StudentViewModel::class.java]

        btCreate.setOnClickListener {
            createStudent()
            clearInput()
        }

        btClear.setOnClickListener{
            clearInput()
        }
    }

    private fun createStudent(){
        viewModel.insertStudent(Student(
            0,
            etName.text.toString(),
            etEmail.text.toString()
        ))
    }

    private fun clearInput(){
        etName.setText("")
        etEmail.setText("")
    }
}