package com.example.template.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.template.model.*
import com.example.template.repository.Repository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {
    val myResponseUsers: MutableList<User> = mutableListOf<User>()
    val myCResponse: MutableLiveData<Response<CResponse>> = MutableLiveData()
    val myString: MutableLiveData<String> = MutableLiveData()
    val myStringResponse: MutableLiveData<Response<String>> = MutableLiveData()
    val myJSONResponse: MutableLiveData<JSONObject> = MutableLiveData()
    val myErrorCodeResponse: MutableLiveData<Int> = MutableLiveData()
    val myUnitResponse: MutableLiveData<Response<Unit>> = MutableLiveData()
    val myDataResponse: MutableLiveData<Response<singleFieldResponseClass?>> = MutableLiveData()
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
                myDataResponse.value = response
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
                myDataResponse.value = response
            else
                myErrorCodeResponse.value = response.code()
        }
    }
    fun check() {
        viewModelScope.launch {
            val response = repository.check()
			if (response.body() != null)
				myDataResponse.value = response
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
			} else
				myErrorCodeResponse.value = response.code()
		}
	}
	fun getUserById(id: Int) {
		viewModelScope.launch {
			val response = repository.getUserById(id)
			if (response.code() == 200) {
				myUserResponse.value = response.body()
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
			} else
				myErrorCodeResponse.value = response.code()
		}
	}
	fun edit(user: User, oldEmail: String) {
		viewModelScope.launch {
			val response = repository.edit(user, oldEmail)
			if (response.code() == 201) {
				myString.value = "SUCCESS"
			} else
				myErrorCodeResponse.value = response.code()
		}
	}
	fun delete(id: Int) {
		viewModelScope.launch {
			val response = repository.delete(id)
			if (response.code() == 204)
				myString.value = "DELETED_SUCCESSFULLY"
			else
				myErrorCodeResponse.value = response.code()
		}
	}
}