package com.example.template.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.template.model.*
import com.example.template.repository.Repository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {
    val myResponseUsers: MutableList<User> = mutableListOf<User>()
    val myResponsePublicRequests: MutableList<PublicRequest> = mutableListOf<PublicRequest>()
    val myResponsePrivateRequests: MutableList<PrivateRequest> = mutableListOf<PrivateRequest>()
    val myCResponse: MutableLiveData<Response<CResponse>> = MutableLiveData()
    val myString: MutableLiveData<String> = MutableLiveData()
    val myStringResponse: MutableLiveData<Response<String>> = MutableLiveData()
    val myJSONResponse: MutableLiveData<JSONObject> = MutableLiveData()
    val myErrorCodeResponse: MutableLiveData<Int> = MutableLiveData()
    val myUnitResponse: MutableLiveData<Response<Unit>> = MutableLiveData()
    val myDataResponse: MutableLiveData<Response<singleFieldResponseClass?>> = MutableLiveData()
    val myTokensResponse: MutableLiveData<Response<Tokens?>> = MutableLiveData()
	val myUserResponse: MutableLiveData<User> = MutableLiveData()

    fun register(
        email: String,
        password: String,
        firstname: String,
        secondname: String,
        patronymic: String,
		telegramUrl: String
    ) {
        viewModelScope.launch {
			val name = firstname.plus(' '.plus(secondname).plus(' '.plus(patronymic)))
            val response = repository.register(email, password, name, telegramUrl)
            if (response.body() != null)
				myTokensResponse.value = response
            else
                myErrorCodeResponse.value = response.code()
        }
    }
    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            val response = repository.login(email, password)
            if (response.body() != null)
				myTokensResponse.value = response
            else
                myErrorCodeResponse.value = response.code()
        }
    }
	fun refreshTokens() {
		viewModelScope.launch {
			val response = repository.refreshTokens()
			if (response.body() != null)
				myTokensResponse.value = response
			else
				myErrorCodeResponse.value = response.code()
		}
	}
	fun revoke() {
		viewModelScope.launch {
			val response = repository.revoke()
			if (response.code() == 200) {
				myString.value = "REVOKED_SUCCESSFULLY"
			}
			else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				revoke()
			}
			else
				myErrorCodeResponse.value = response.code() // most likely 401
		}
	}
    fun check() {
        viewModelScope.launch {
            val response = repository.check()
			if (response.body() != null)
				myDataResponse.value = response
			else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				check()
			}
			else
				myErrorCodeResponse.value = response.code()
        }
    }

	fun getUsers() {
		viewModelScope.launch {
			val response = repository.getUsers()
			if (response.code() == 200) {
				myResponseUsers.clear()
				myResponseUsers.addAll(0, response.body() ?: mutableListOf<User>())
				myString.value = "GOTUSERS"
			} else if (response.code() == 204) {
				myResponseUsers.clear()
			} else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				getUsers()
			} else
				myErrorCodeResponse.value = response.code()
		}
	}
	fun getUserById(id: Int) {
		viewModelScope.launch {
			val response = repository.getUserById(id)
			if (response.code() == 200) {
				myUserResponse.value = response.body()
			} else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				getUserById(id)
			} else
				myErrorCodeResponse.value = response.code()
		}
	}
	fun getUserByEmail(email: String) {
		viewModelScope.launch {
			val response = repository.getUserByEmail(email)
			Log.i("getUserByEmail", email)
			if (response.code() == 200) {
				myUserResponse.value = response.body()
			} else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				getUserByEmail(email)
			} else
				myErrorCodeResponse.value = response.code()
		}
	}
	fun create(
		email: String,
		password: String,
		firstname: String,
		secondname: String,
		patronymic: String,
		telegramUrl: String
	) {
		viewModelScope.launch {
			val name = firstname.plus(' '.plus(secondname).plus(' '.plus(patronymic)))
			val response = repository.create(email, password, name, telegramUrl)
			if (response.code() == 201) {
				myString.value = "SUCCESS"
			} else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				create(email, password, firstname, secondname, patronymic, telegramUrl)
			} else
				myErrorCodeResponse.value = response.code()
		}
	}
	fun edit(user: User, oldEmail: String) {
		viewModelScope.launch {
			val response = repository.edit(user, oldEmail)
			if (response.code() == 201) {
				myString.value = "SUCCESS"
			} else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				edit(user, oldEmail)
			} else
				myErrorCodeResponse.value = response.code()
		}
	}
	fun delete(id: Int) {
		viewModelScope.launch {
			val response = repository.delete(id)
			if (response.code() == 204)
				myString.value = "DELETED_SUCCESSFULLY"
			else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				delete(id)
			} else
				myErrorCodeResponse.value = response.code()
		}
	}

	fun getPublicRequests() {
		viewModelScope.launch {
			val response = repository.getPublicRequests()
			if (response.code() == 200) {
				myResponsePublicRequests.clear()
				myResponsePublicRequests.addAll(0, response.body() ?: mutableListOf<PublicRequest>())
				myString.value = "GOT_REQUESTS"
			} else if (response.code() == 204) {
				myResponsePublicRequests.clear()
			} else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				getPublicRequests()
			} else
				myErrorCodeResponse.value = response.code()
		}
	}
	fun assignMe(id: Int) {
		viewModelScope.launch {
			val response = repository.assignMe(id)
			if (response.code() == 204)
				myString.value = "ASSIGNED_SUCCESSFULLY"
			else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				assignMe(id)
			} else
				myErrorCodeResponse.value = response.code()
		}
	}
	fun unassignMe(id: Int) {
		viewModelScope.launch {
			val response = repository.unassignMe(id)
			if (response.code() == 204)
				myString.value = "UNASSIGNED_SUCCESSFULLY"
			else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				unassignMe(id)
			} else
				myErrorCodeResponse.value = response.code()
		}
	}

	fun getMyProfile() {
		viewModelScope.launch {
			val response = repository.getMyProfile()
			if (response.code() == 200)
				myUserResponse.value = response.body()
			else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				getMyProfile()
			} else
				myErrorCodeResponse.value = response.code()
		}
	}


	fun promoteToAdmin(userEmail: String) {
		Log.i("stage", "1")
		viewModelScope.launch {
			val response = repository.promoteToAdmin(userEmail)
			if (response.code() == 204)
				myString.value = "SUCCESS"
			else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				promoteToAdmin(userEmail)
			} else
				myErrorCodeResponse.value = response.code()
		}
	}

	fun demoteToStudent(userEmail: String) {
		viewModelScope.launch {
			val response = repository.demoteToStudent(userEmail)
			if (response.code() == 204)
				myString.value = "SUCCESS"
			else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				demoteToStudent(userEmail)
			} else
				myErrorCodeResponse.value = response.code()
		}
	}

	fun getPrivateRequests() {
		viewModelScope.launch {
			val response = repository.getPrivateRequests()
			if (response.code() == 200) {
				myResponsePrivateRequests.clear()
				myResponsePrivateRequests.addAll(0, response.body() ?: mutableListOf<PrivateRequest>())
				myString.value = "GOT_REQUESTS"
			} else if (response.code() == 204)
				myResponsePrivateRequests.clear()
			else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				getPrivateRequests()
			} else
				myErrorCodeResponse.value = response.code()
		}
	}

	fun createRequest(request: PrivateRequest) {
		viewModelScope.launch {
			val response = repository.createRequest(request)
			if (response.code() == 201)
				myString.value = "CREATED_REQUEST_SUCCESSFULLY"
			else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				createRequest(request)
			} else
				myErrorCodeResponse.value = response.code()
		}
	}

	fun deleteRequest(id: Int) {
		viewModelScope.launch {
			val response = repository.deleteRequest(id)
			if (response.code() == 204)
				myString.value = "DELETED_REQUEST_SUCCESSFULLY"
			else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				deleteRequest(id)
			} else
				myErrorCodeResponse.value = response.code()
		}
	}

	fun markAsCompleted(requestId: Int, usersIds: List<Int>) {
		viewModelScope.launch {
			val response = repository.markAsCompleted(requestId, usersIds)
			if (response.code() == 204)
				myString.value = "MARKED_AS_COMPLETED_SUCCESSFULLY"
			else if (response.code() == 401) { // we gotta refresh it
				refreshTokens()
				markAsCompleted(requestId, usersIds)
			} else
				myErrorCodeResponse.value = response.code()
		}
	}
}