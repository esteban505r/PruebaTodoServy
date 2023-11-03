package com.esteban.lopez.pruebatodoservy.di

import android.content.Context
import androidx.room.Room
import com.esteban.lopez.pruebatodoservy.model.datasource.TasksLocalDataSource
import com.esteban.lopez.pruebatodoservy.model.model.db.AppDatabase
import com.esteban.lopez.pruebatodoservy.model.repository.TasksRepository
import com.esteban.lopez.pruebatodoservy.viewmodel.CreateTaskViewModel
import com.esteban.lopez.pruebatodoservy.viewmodel.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(get())
    }
    viewModel{
        CreateTaskViewModel(get())
    }
}

val repositoryModule = module {
    single {
        TasksRepository(get())
    }
}

val datasourceModule = module{
    single {
        TasksLocalDataSource(get())
    }
}

val roomModule = module {
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "tasks-db"
        ).build()
    }

    single { provideDatabase(androidContext()) }
    single { provideDatabase(androidContext()).taskDao()}
}
