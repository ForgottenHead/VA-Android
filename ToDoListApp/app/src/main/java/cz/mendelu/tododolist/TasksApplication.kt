package cz.mendelu.tododolist

import android.app.Application
import cz.mendelu.tododolist.di.daoModule
import cz.mendelu.tododolist.di.databaseModule
import cz.mendelu.tododolist.di.repositoryModule
import cz.mendelu.tododolist.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TasksApplication: Application() {
    //aplikacnu triedu treba pouzit -> Napisat do manifestu

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(applicationContext)

            modules(databaseModule, daoModule, repositoryModule, viewModelModule)


        }
    }
}