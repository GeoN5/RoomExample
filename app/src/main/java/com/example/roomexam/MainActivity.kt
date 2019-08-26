package com.example.roomexam

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //DB는 백그라운드에서 동작되지 않으면 에러
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "todo-db")
            .allowMainThreadQueries() //Main Thread허용 (실제로는 background Thread 에서 작업)
            .build()

        showResult(db)

        addButton.setOnClickListener {
            db.todoDao().insert(Todo(todoEdit.text.toString()))
            Toast.makeText(this,"할 일 추가!", Toast.LENGTH_SHORT).show()
            showResult(db)
        }

    }

    private fun showResult(db:AppDatabase){
        resultText.text = db.todoDao().getAll().toString()
    }

}
