package com.test.italika.network.services

import com.test.italika.models.User
import retrofit2.http.GET

interface UserService {
    @GET("feeds")
    suspend fun loadUsers():List<User>
}