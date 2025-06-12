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
import retrofit2.http.PUT
import retrofit2.http.Query

interface API {

    @POST("/api/Auth/Registration")
    @Headers("Content-Type: application/json")
    suspend fun register(@Body temp: User) : Response<Tokens?> // Back-End returns codes: 201 + tokens, 409, 500

    @POST("/api/Auth/Login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body temp: LogInForm) : Response<Tokens?> // Back-End returns codes: 200 + tokens, 401, 500

	@DELETE("/api/Auth/Revoke")
	@Headers("Content-Type: application/json")
	suspend fun revoke(@Header("Authorization") token: String) : Response<Unit> // returns 200 + nothing or 401 + nothing

	@GET("/api/Auth/RefreshTokens")
	@Headers("Content-Type: application/json")
	suspend fun refresh(@Query("oldRefreshToken") RefreshToken: String) : Response<Tokens?> // returns 200 + tokens or 404 + nothing

	@GET("/api/Auth/Check")
    suspend fun check(@Header("Authorization") token: String) : Response<singleFieldResponseClass?> // returns role


	@GET("/api/StudentRequest/PublicFeed")
	suspend fun getPublicRequests(@Header("Authorization") token: String) : Response<MutableList<PublicRequest>>

	@PUT("/api/StudentRequest/AssignMe")
	suspend fun assignMe(@Header("Authorization") token: String, @Query("requestId") id: Int) : Response<Unit>

	@DELETE("/api/StudentRequest/UnassignMe")
	suspend fun unassignMe(@Header("Authorization") token: String, @Query("requestId") id: Int) : Response<Unit>

	@GET("/api/AdminRequest/AdminFeed")
	suspend fun getPrivateRequests(@Header("Authorization") token: String) : Response<MutableList<PrivateRequest>>

	@POST("/api/AdminRequest/CreateRequest")
	suspend fun createRequest(@Header("Authorization") token: String, @Body request: PrivateRequest) : Response<Unit> // returns either 403 or 200

	@DELETE("/api/AdminRequest/DeleteRequest")
	suspend fun deleteRequest(@Header("Authorization") token: String, @Query("id") id: Int) : Response<Unit>

	@GET("/api/AdminUser/GetUsers")
	suspend fun getUsers(@Header("Authorization") token: String) : Response<MutableList<User>>

	@GET("/api/AdminUser/GetUserById")
	suspend fun getUserById(@Header("Authorization") token: String, @Query("id") id: Int) : Response<User>

	@GET("/api/AdminUser/GetUserByEmail")
	suspend fun getUserByEmail(@Header("Authorization") token: String, @Query("email") email: String) : Response<User>

	@POST("/api/AdminUser/Create")
	suspend fun create(@Header("Authorization") token: String, @Body temp: User) : Response<JSONObject>

	@POST("/api/AdminUser/Edit")
	suspend fun edit(@Header("Authorization") token: String, @Body user: User, @Query("oldEmail") email: String) : Response<CResponse>

	@DELETE("/api/AdminUser/DeleteUser")
	suspend fun delete(@Header("Authorization") token: String, @Query("id") id: Int) : Response<Unit>


	@GET("/api/User/GetMyProfile")
	suspend fun getMyProfile(@Header("Authorization") token: String) : Response<User>

	@PUT("/api/DevUser/PromoteToAdmin")
	suspend fun promoteToAdmin(@Header("Authorization") token: String, @Query("userEmail") email: String) : Response<Unit>

	@PUT("/api/DevUser/DemoteToStudent")
	suspend fun demoteToStudent(@Header("Authorization") token: String, @Query("userEmail") email: String) : Response<Unit>

}