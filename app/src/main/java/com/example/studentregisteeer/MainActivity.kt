package com.example.studentregisteeer

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentregisteeer.db.Student
import com.example.studentregisteeer.db.StudentDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var etName:EditText
    private lateinit var etEmail:EditText
    private lateinit var btCreate:Button
    private lateinit var btClear:Button
    private lateinit var viewModel: StudentViewModel
    private lateinit var rvStudents: RecyclerView
    private lateinit var adapter: StudentRecyclerViewAdapter
    private lateinit var selecteStudent: Student
    private var isStudentSelected = false

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
        rvStudents = findViewById(R.id.rvStudents)

        val dao = StudentDatabase.getInstance(application).studentDao()
        val factory = StudentViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory)[StudentViewModel::class.java]

        btCreate.setOnClickListener {
            if(isStudentSelected){
                updateStudent()
            } else {
                createStudent()
            }
            clearInput()
        }

        btClear.setOnClickListener{
            if(isStudentSelected){
                deleteStudent()
            }
            clearInput()
        }

        initRecyclerView()
    }

    private fun initRecyclerView(){
        rvStudents.layoutManager = LinearLayoutManager(this)
        adapter = StudentRecyclerViewAdapter { selectedStudent: Student ->
            listItemClicked(selectedStudent)
        }
        rvStudents.adapter = adapter

        displayStudents()
    }

    private fun listItemClicked(student: Student) {
        selecteStudent = student
        btCreate.text = "Update"
        btClear.text = "Delete"
        isStudentSelected = true

        etName.setText(student.name)
        etEmail.setText(student.email)
    }

    private fun displayStudents(){
        viewModel.students.observe(this) {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun createStudent(){
        viewModel.insertStudent(Student(
            0,
            etName.text.toString(),
            etEmail.text.toString()
        ))
    }

    private fun updateStudent(){
        viewModel.updateStudent(Student(
            selecteStudent.id,
            etName.text.toString(),
            etEmail.text.toString()
        ))

        btCreate.text = "Create"
        btClear.text = "Clear"
        isStudentSelected = false
    }

    private fun deleteStudent(){
        viewModel.deleteStudent(selecteStudent)

        btCreate.text = "Create"
        btClear.text = "Clear"
        isStudentSelected = false
    }

    private fun clearInput(){
        etName.setText("")
        etEmail.setText("")
    }
}