package com.codecamp.fitnessapp.data.user

import com.codecamp.fitnessapp.model.User

/*
 * Acts as a single source of truth. Returns user data.
 */
interface UserRepository {
    suspend fun insertUser(user: User)
}

@kotlinx.serialization.ExperimentalSerializationApi
class DefaultUserRepository(
    private val userDao: UserDao
) : UserRepository {
    /*
     * Acts as a reference to the weather stored in the database.
     * This state flow is viewed by the viewmodel.
     */
    val user = userDao.getUser(0)

    override suspend fun insertUser(user: User) = userDao.insert(user)
}