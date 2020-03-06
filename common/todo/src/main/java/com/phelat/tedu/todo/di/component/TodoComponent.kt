package com.phelat.tedu.todo.di.component

import android.content.Context
import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.todo.di.module.TodoBindingModule
import com.phelat.tedu.todo.di.module.TodoDatabaseModule
import com.phelat.tedu.todo.di.scope.TodoScope
import com.phelat.tedu.todo.entity.TodoEntity
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.flow.Flow

@TodoScope
@Component(modules = [TodoBindingModule::class, TodoDatabaseModule::class])
interface TodoComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        // TODO: USE QUALIFIER
        fun context(context: Context): Builder

        fun build(): TodoComponent
    }

    fun todoWritableDataSource(): Writable.Suspendable<TodoEntity>

    fun todoReadableDataSource(): Readable<Flow<List<TodoEntity>>>

    fun todoUpdatableDataSource(): Updatable.Suspendable<TodoEntity>

    fun todoDeletableDataSource(): Deletable.Suspendable<TodoEntity>
}