package com.example.template.repository

import android.util.Log
import com.example.template.api.RetrofitInstance
import com.example.template.functions.data_manipulation.globalAccessToken
import com.example.template.functions.data_manipulation.globalRefreshToken
import com.example.template.model.*
import org.json.JSONObject
//import com.example.trashhack.model.loggedin.LoggedInUser_instance
import retrofit2.Response

class Repository {
    suspend fun register(
        email: String,
        password: String,
        name: String,
		telegramUrl: String
    ) : Response<Tokens?> {
        val temp = User(
            0,
            email,
            password,
			name,
            telegramUrl,
			" ",
			" ",
			0,
			0
        )
        return RetrofitInstance.api.register(temp)
    }

    suspend fun login(
        email: String,
        password: String
    ) : Response<Tokens?>
    {
        val temp = LogInForm(
            email,
            password
        )
        return RetrofitInstance.api.login(temp)
    }

	suspend fun revoke() : Response<Unit> {
		return RetrofitInstance.api.revoke(
			"Bearer ".plus(globalAccessToken.value ?: "")
		)
	}

	suspend fun refreshTokens() : Response<Tokens?> {
		return RetrofitInstance.api.refresh(globalRefreshToken.value.toString())
	}

	suspend fun check() : Response<singleFieldResponseClass?> {
        return RetrofitInstance.api.check(
			"Bearer ".plus(globalAccessToken.value ?: "")
		)
    }


	suspend fun getUsers() : Response<MutableList<User>> {
		return RetrofitInstance.api.getUsers(
			"Bearer ".plus(globalAccessToken.value ?: "")
		)
	}
	suspend fun getUserById(id: Int) : Response<User> {
		return RetrofitInstance.api.getUserById(
			"Bearer ".plus(globalAccessToken.value ?: ""),
			id
		)
	}
	suspend fun getUserByEmail(email: String) : Response<User> {
		Log.i("getUserByEmail", email)
		return RetrofitInstance.api.getUserByEmail(
			"Bearer ".plus(globalAccessToken.value ?: ""),
			email
		)
	}
	suspend fun create(
		email: String,
		password: String,
		name: String,
		telegramUrl: String
	) : Response<JSONObject> {
		val temp = User(
			0,
			email,
			password,
			name,
			telegramUrl,
			" ",
			" ",
			0,
			0
		)
		return RetrofitInstance.api.create(
			"Bearer ".plus(globalAccessToken.value ?: ""),
			temp
		)
	}
	suspend fun edit(user: User, email: String) : Response<CResponse> {
		return RetrofitInstance.api.edit(
			"Bearer ".plus(globalAccessToken.value ?: ""),
			user,
			email
		)
	}
	suspend fun delete(id: Int) : Response<Unit> {
		return RetrofitInstance.api.delete(
			"Bearer ".plus(globalAccessToken.value ?: ""),
			id
		)
	}


	suspend fun getPublicRequests() : Response<MutableList<PublicRequest>> {
		return RetrofitInstance.api.getPublicRequests(
			"Bearer ".plus(globalAccessToken.value ?: "")
		)
	}
	suspend fun assignMe(id: Int) : Response<Unit> {
		return RetrofitInstance.api.assignMe(
			"Bearer ".plus(globalAccessToken.value ?: ""),
			id
		)
	}
	suspend fun unassignMe(id: Int) : Response<Unit> {
		return RetrofitInstance.api.unassignMe(
			"Bearer ".plus(globalAccessToken.value ?: ""),
			id
		)
	}

	suspend fun getMyProfile() : Response<User> {
		return RetrofitInstance.api.getMyProfile(
			"Bearer ".plus(globalAccessToken.value ?: "")
		)
	}

	suspend fun promoteToAdmin(email: String) : Response<Unit> {
		Log.i("stage", "2")
		return RetrofitInstance.api.promoteToAdmin(
			"Bearer ".plus(globalAccessToken.value ?: ""),
			email
		)
	}
	suspend fun demoteToStudent(email: String) : Response<Unit> {
		return RetrofitInstance.api.demoteToStudent(
			"Bearer ".plus(globalAccessToken.value ?: ""),
			email
		)
	}

	suspend fun getPrivateRequests() : Response<MutableList<PrivateRequest>> {
		return RetrofitInstance.api.getPrivateRequests(
			"Bearer ".plus(globalAccessToken.value ?: "")
		)
	}

	suspend fun createRequest(request: PrivateRequest) : Response<Unit> {
		Log.i("REQUESTBODY", request.toString())
		return RetrofitInstance.api.createRequest(
			"Bearer ".plus(globalAccessToken.value ?: ""),
			request
		)
	}

	suspend fun deleteRequest(id: Int) : Response<Unit> {
		return RetrofitInstance.api.deleteRequest(
			"Bearer ".plus(globalAccessToken.value ?: ""),
			id
		)
	}
}

