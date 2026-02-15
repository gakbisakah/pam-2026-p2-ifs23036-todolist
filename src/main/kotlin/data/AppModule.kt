package com.delcom.data

import com.delcom.repositories.ITodoRepository
import com.delcom.repositories.TodoRepository
import com.delcom.services.ITodoService
import com.delcom.controllers.TodoController
import com.delcom.services.TodoService
import org.koin.dsl.module
val todoModule = module {
    //Repository
    single<ITodoRepository>{
        TodoRepository()
    }

    // Service
    single<ITodoService>{
        TodoService(get())
    }

    // Controller
    single{
        TodoController(get())
    }
}