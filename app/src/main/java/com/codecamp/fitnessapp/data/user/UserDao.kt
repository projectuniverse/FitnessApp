package com.codecamp.fitnessapp.data.user

import androidx.room.*
import com.codecamp.fitnessapp.model.User
import kotlinx.coroutines.flow.Flow

/*
 * Makes the requests to the Room database
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUser(id: Int): Flow<User>

    @Query("SELECT EXISTS(SELECT * FROM user)")
    fun userExists(): Flow<Boolean>
}