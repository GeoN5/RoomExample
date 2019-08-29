package com.example.roomexam

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //DB는 백그라운드에서 동작되지 않으면 에러
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "todo-db")
            .build()

        //UI 갱신
        db.todoDao().getAll().observe(this, Observer {
            resultText.text = it.toString()
        })

        //버튼 클릭 시 DB에 insert
        addButton.setOnClickListener {
            //background async
            lifecycleScope.launch(Dispatchers.IO) {
                db.todoDao().insert(Todo(todoEdit.text.toString()))
            }
            Toast.makeText(this, "할 일 추가!", Toast.LENGTH_SHORT).show()
        }

    }

}
