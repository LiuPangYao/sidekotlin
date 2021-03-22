package com.slideproject.sidekotlin.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(val dataStore: DataStore<Preferences>) {

    //Create some keys
    companion object {
        val USER_AGE_KEY = intPreferencesKey("USER_AGE")
        val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
        val USER_COMPANY_KEY = stringPreferencesKey("USER_COMPANY")
    }

    //Store user data
    suspend fun storeUser(age: Int, name: String, company: String) {
        dataStore.edit {
            it[USER_AGE_KEY] = age
            it[USER_NAME_KEY] = name
            it[USER_COMPANY_KEY] = company
        }
    }

    //Create an age flow
    val userAgeFlow: Flow<Int?> = dataStore.data.map {
        it[USER_AGE_KEY]
    }

    //Create a name flow
    val userNameFlow: Flow<String?> = dataStore.data.map {
        it[USER_NAME_KEY]
    }

    //Create a company flow
    val userCompanyFlow: Flow<String?> = dataStore.data.map {
        it[USER_COMPANY_KEY]
    }
}