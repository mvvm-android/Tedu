package com.phelat.tedu.todo.di.module

import androidx.room.Room
import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.ModuleContainer
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.database.TodoDatabase
import com.phelat.tedu.todo.database.entity.TodoDatabaseEntity
import com.phelat.tedu.todo.datasource.TodoDataSource
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.mapper.TodoDatabaseEntityToTodoEntity
import kotlinx.coroutines.flow.Flow
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import java.util.Date

object TodoModule : ModuleContainer {

    override fun getModule() = module {
        single {
            Room.databaseBuilder(androidContext(), TodoDatabase::class.java, "todo_database")
                .build()
        }
        single { get<TodoDatabase>().todoEntityDao() }
        single { TodoDatabaseEntityToTodoEntity() }
            .bind<Mapper<TodoDatabaseEntity, TodoEntity>>()
        single { TodoDataSource(get(), get()) }
            .bind<Writable.Suspendable<TodoEntity>>()
            .bind<Readable.IO<Date, Flow<List<TodoEntity>>>>()
            .bind<Updatable.Suspendable<TodoEntity>>()
            .bind<Deletable.Suspendable<TodoEntity>>()
    }
}