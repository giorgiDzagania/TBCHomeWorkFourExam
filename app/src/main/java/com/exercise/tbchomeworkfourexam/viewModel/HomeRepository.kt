package com.exercise.tbchomeworkfourexam.viewModel

import com.exercise.tbchomeworkfourexam.model.UserData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

object RepositoryNet{
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://run.mocky.io/v3/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

class HomeRepository {
    private val apiServices = RepositoryNet.apiService
    suspend fun getUserData(): List<UserData>{
        return apiServices.getUsers()
    }
}

interface ApiService {
    @GET("744fa574-a483-43f6-a1d7-c65c87b5d9b2")
    suspend fun getUsers(): List<UserData>
}