package com.delcom.repositories

import com.delcom.entities.Todo

interface ITodoRepository {
    fun getAll(): List<Todo>
    fun getById(id: String): Todo?
    fun create(title: String, description: String) : String
    fun update(id: String, title: String, description: String, isDone: Boolean): Boolean
    fun delete(id: String) : Boolean
}