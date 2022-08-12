package com.test.italika.repositories

import com.test.italika.models.User
import com.test.italika.network.services.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val userService: UserService) {

    suspend fun loadFeeds() : Flow<List<User>> = flow{
        val feeds = userService.loadUsers()
        emit(feeds)
    }
}