package com.example.template.repository

import android.util.Log
import com.example.template.api.RetrofitInstance
import com.example.template.functions.data_manipulation.globalToken
import com.example.template.model.*
import org.json.JSONObject
//import com.example.trashhack.model.loggedin.LoggedInUser_instance
import retrofit2.Response

class Repository {
    /*
    suspend fun getPosts(): Posts {
        return RetrofitInstance.api.getposts()
    }
     */
    /*
    suspend fun getDevs(): Response<MutableList<Users>> {
        return RetrofitInstance.api.getdevs(userhash.value ?: "")
    }
    suspend fun getRole(): Response<String> {
        return RetrofitInstance.api.getrole(userhash.value ?: "")
    }
    LEGACY
     */

    /*
    suspend fun pushPostUsers(
        id: Int,
        email: String,
        passwordhash: String,
        fullname: String,
        role: String,
        organizationid: Int,
        done: Int,
        score: Int,
        current: String
    ): Response<Users> {
        var temp: Users = Users(id, email, passwordhash, fullname, role, organizationid, done, score, current)
        return RetrofitInstance.api.pushpost(temp)
    }
    LEGACY
     */
    /*
    suspend fun devregister(
        email: String,
        password: String,
        fullname: String,
        role: String,
        organizationid: Int
    ) : Response<String> {
        val temp: DevRegForm = DevRegForm(
            email,
            password,
            fullname,
            role,
            organizationid,
            userhash.value ?: ""
        )
        return RetrofitInstance.api.devregister(temp)
    }
    LEGACY
     */
    suspend fun register(
        email: String,
        password: String,
        firstname: String,
        secondname: String
    ) : Response<TokenResponseClass?> {
        val temp = User(
            0,
            email,
            password,
            firstname,
            secondname
        )
        return RetrofitInstance.api.register(temp)
    }
    /*
    suspend fun createSave(
        email: String,
        password: String,
        firstname: String,
        secondname: String
    ) : Response<Unit> {
        val temp: Users = Users(
            0,
            email,
            password,
            firstname,
            secondname
        )
        return RetrofitInstance.api.createSave(temp, globalOldEmail.value.toString(), )
    }

     */
    /*
    suspend fun delete(
        email: String,
        password_hash: String,
        fullname: String,
        role: String,
        organizationid: Int
    ) : Response<String> {
        val temp: DevRegForm = DevRegForm(
            email,
            password_hash,
            fullname,
            role,
            organizationid,
            userhash.value ?: ""
        )
        return RetrofitInstance.api.delete(temp)
    }
    LEGACY
     */

    suspend fun login(
        email: String,
        password: String
    ) : Response<TokenResponseClass?>
    {
        val temp = LogInForm(
            email,
            password
        )
        return RetrofitInstance.api.login(temp)
    }
    suspend fun check() : Response<Unit> {
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
		firstname: String,
		secondname: String
	) : Response<JSONObject> {
		val temp = User(
			0,
			email,
			password,
			firstname,
			secondname
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
}

