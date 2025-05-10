package com.example.template.repository

import android.util.Log
import com.example.template.api.RetrofitInstance
import com.example.template.functions.data_manipulation.globalToken
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
    ) : Response<singleFieldResponseClass?> {
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
    ) : Response<singleFieldResponseClass?>
    {
        val temp = LogInForm(
            email,
            password
        )
        return RetrofitInstance.api.login(temp)
    }
	suspend fun check() : Response<singleFieldResponseClass?> {
        return RetrofitInstance.api.check(
            "Bearer ".plus(globalToken.value ?: "")
        )
    }


	suspend fun getUsers() : Response<MutableList<User>> {
		return RetrofitInstance.api.getUsers(
			"Bearer ".plus(globalToken.value ?: "")
		)
	}
	suspend fun getUserById(id: Int) : Response<User> {
		return RetrofitInstance.api.getUserById(
			"Bearer ".plus(globalToken.value ?: ""),
			id
		)
	}
	suspend fun getUserByEmail(email: String) : Response<User> {
		Log.i("getUserByEmail", email)
		return RetrofitInstance.api.getUserByEmail(
			"Bearer ".plus(globalToken.value ?: ""),
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
			"Bearer ".plus(globalToken.value ?: ""),
			temp
		)
	}
	suspend fun edit(user: User, email: String) : Response<CResponse> {
		return RetrofitInstance.api.edit(
			"Bearer ".plus(globalToken.value ?: ""),
			user,
			email
		)
	}
	suspend fun delete(id: Int) : Response<Unit> {
		return RetrofitInstance.api.delete(
			"Bearer ".plus(globalToken.value ?: ""),
			id
		)
	}


	suspend fun getPublicRequests() : Response<MutableList<PublicRequest>> {
		return RetrofitInstance.api.getPublicRequests(
			"Bearer ".plus(globalToken.value ?: "")
		)
	}
	suspend fun assignMe(id: Int) : Response<Unit> {
		return RetrofitInstance.api.assignMe(
			"Bearer ".plus(globalToken.value ?: ""),
			id
		)
	}
	suspend fun unassignMe(id: Int) : Response<Unit> {
		return RetrofitInstance.api.unassignMe(
			"Bearer ".plus(globalToken.value ?: ""),
			id
		)
	}

	suspend fun getMyProfile() : Response<User> {
		return RetrofitInstance.api.getMyProfile(
			"Bearer ".plus(globalToken.value ?: "")
		)
	}

}

