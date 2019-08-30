package com.example.roomexam

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.roomexam.db.Todo
import com.example.roomexam.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val mainViewModel:MainViewModel by lazy {
        ViewModelProviders.of(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //UI 갱신
        mainViewModel.getAll().observe(this, Observer {
            resultText.text = it.toString()
        })

        setListeners()

    }

    private fun setListeners(){

        //insert
        addButton.setOnClickListener {
            if (todoEdit.text?.isNotEmpty()!!) {
                //background async
                lifecycleScope.launch(Dispatchers.IO) {
                    mainViewModel.insert(Todo(todoEdit.text.toString()))
                }
                Toast.makeText(this, "할 일 추가!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "할 일을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        //delete
        deleteButton.setOnClickListener {
            if (todoEdit.text?.isNotEmpty()!!) {
                //background async
                lifecycleScope.launch(Dispatchers.IO) {
                    mainViewModel.delete(Todo(todoEdit.text.toString()))
                }
                Toast.makeText(this, "삭제 되었습니다.!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "삭제할 일을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
