package com.example.roomexam.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.roomexam.db.AppDatabase
import com.example.roomexam.db.Todo

//need context -> AndroidViewModel
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(application, AppDatabase::class.java, "todo-db").build()


    fun getAll(): LiveData<List<Todo>> = db.todoDao().getAll()

    //coroutine scope안에서 동작하도록 강제
    suspend fun insert(todo: Todo) = db.todoDao().insert(todo)


}