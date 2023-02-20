package com.codecamp.fitnessapp.data.user

import com.codecamp.fitnessapp.model.User
import kotlinx.coroutines.flow.first

/*
 * Acts as a single source of truth. Returns user data.
 */
interface UserRepository {
    suspend fun getUser(): User?
    suspend fun updateUser(user: User)
    suspend fun insertUser(user: User)
}

@kotlinx.serialization.ExperimentalSerializationApi
class DefaultUserRepository(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun getUser(): User? {
        if (userDao.userExists().first()) {
            return userDao.getUser(0).first()
        }
        return null
    }
    override suspend fun updateUser(user: User) = userDao.update(user)
    override suspend fun insertUser(user: User) = userDao.insert(user)
}