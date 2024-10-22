package com.example.studentregisteeer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentregisteeer.db.Student

class StudentRecyclerViewAdapter() : RecyclerView.Adapter<StudentViewHolder>() {
    private val studentList = ArrayList<Student>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item, parent, false)
        return StudentViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentList[position])
    }

    fun setList(students: List<Student>){
        studentList.clear()
        studentList.addAll(students)
    }
}

class StudentViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
    fun bind(student: Student){
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)

        tvName.text = student.name
        tvEmail.text = student.email
    }
}