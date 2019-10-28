package com.mylektop.simplecontact.di

import com.mylektop.simplecontact.data.repository.ContactRepository
import org.koin.dsl.module.applicationContext

/**
 * Created by MyLektop on 23/10/2019.
 */
val RepositoryModules = applicationContext {
    bean { ContactRepository(get(), get(), get(), get()) }
}