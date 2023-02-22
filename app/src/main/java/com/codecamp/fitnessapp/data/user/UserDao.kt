package com.codecamp.fitnessapp.data.user

import androidx.room.*
import com.codecamp.fitnessapp.model.User
import kotlinx.coroutines.flow.Flow

/*
 * Makes the requests to the Room database
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM user WHERE id = 0")
    fun getUser(): Flow<User>
}