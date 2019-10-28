package com.mylektop.simplecontact.di

import com.mylektop.simplecontact.ui.main.MainActivity
import com.mylektop.simplecontact.ui.main.MainActivityContract
import com.mylektop.simplecontact.ui.main.MainActivityPresenter
import org.koin.dsl.module.applicationContext

/**
 * Created by MyLektop on 23/10/2019.
 */
val MainModule = applicationContext {
    factory { MainActivity() }
    factory { MainActivityPresenter(get()) as MainActivityContract.Presenter }
}

val AppModuleComponents = listOf(
    RemoteModule,
    RepositoryModules,
    MainModule
)