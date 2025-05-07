package com.example.template.api

import com.example.template.model.*
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Header
import retrofit2.http.Query

interface API {
    /*
    @GET("posts/1")
    suspend fun getposts() : Posts
    */
    /*
    @POST("devs")
    @Headers("Content-Type: application/json")
    suspend fun getdevs(@Body hash: String) : Response<MutableList<Users>>
    LEGACY
     */
    /*
    @POST("/api/Start/SomePost") // registration is not yet implemented: for testing purposes only.
    @Headers("Content-Type: application/json")
    suspend fun register(@Body temp: Users) : Response<String>
    */
	/*
    @POST("/api/User/Save")
    @Headers("Content-Type: application/json")
    suspend fun createSave(@Body temp: Users, oldEmail: String, IsNew: Boolean) : Response<Unit>
	 */

    @POST("/api/Auth/Registration")
    @Headers("Content-Type: application/json")
    suspend fun register(@Body temp: User) : Response<singleFieldResponseClass?> // Back-End returns codes: 201 + token, 409, 500

    @POST("/api/Auth/Login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body temp: LogInForm) : Response<singleFieldResponseClass?> // Back-End returns codes: 200 + token, 401, 500

    @GET("/api/Auth/Check")
    suspend fun check(@Header("Authorization") token: String) : Response<singleFieldResponseClass?> // returns role

	@GET("/api/User/GetUsers")
	suspend fun getUsers(@Header("Authorization") token: String) : Response<MutableList<User>>

	@GET("/api/User/GetUserById")
	suspend fun getUserById(@Header("Authorization") token: String, @Query("id") id: Int) : Response<User>

	@GET("/api/User/GetUserByEmail")
	suspend fun getUserByEmail(@Header("Authorization") token: String, @Query("email") email: String) : Response<User>

	@POST("/api/User/Create")
	suspend fun create(@Header("Authorization") token: String, @Body temp: User) : Response<JSONObject>


	@POST("/api/User/Edit")
	suspend fun edit(@Header("Authorization") token: String, @Body user: User, @Query("oldEmail") email: String) : Response<CResponse>

	@DELETE("/api/User/DeleteUser")
	suspend fun delete(@Header("Authorization") token: String, @Query("id") id: Int) : Response<Unit>

	/*
	@POST("role") // TODO: change to get request
	@Headers("Content-Type: application/json")
	suspend fun getrole(@Body hash: String) : Response<String>
	 */
    /*
    @POST("user/{user}")
    @Headers("Content-Type: application/json")
    suspend fun gethash(@Path("user") user: String?): Response<Users>

    @POST("hash")
    @Headers("Content-Type: application/json")
    suspend fun gethash(@Body temp: RegistrationForm) : Response<String>

    @POST("hash")
    @Headers("Content-Type: application/json")
    suspend fun gethash(@Body hash: String) : Response<String>
     */
    /*
    @POST("devregistration")
    @Headers("Content-Type: application/json")
    suspend fun devregister(@Body temp: DevRegForm) : Response<String>

    @POST("deletion")
    @Headers("Content-Type: application/json")
    suspend fun delete(@Body temp: DevRegForm) : Response<String> // Password field here is used for a password hash
    LEGACY
     */
}