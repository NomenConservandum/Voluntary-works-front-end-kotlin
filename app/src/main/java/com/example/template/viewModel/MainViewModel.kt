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
    val myTokenResponse: MutableLiveData<Response<TokenResponseClass?>> = MutableLiveData()
	val myUserResponse: MutableLiveData<User> = MutableLiveData()
    /*
    fun getPosts() {
        viewModelScope.launch {
            val response = repository.getPosts()
            myResponse_posts.value = response
        }
    }
     */
    /*
    fun getDevs(context: Context) {
        try {
            viewModelScope.launch {
                val response = repository.getDevs()
                response.body()?.let { myResponse_users.addAll(it) }// = response
            }
        } catch (e: HttpException) {
            Toast.makeText(context, "Try again.", Toast.LENGTH_SHORT).show()
        }
    }
    LEGACY
     */
    fun register(
        email: String,
        password: String,
        firstname: String,
        secondname: String
    ) {
        viewModelScope.launch {
            val response = repository.register(email, password, firstname, secondname)
            if (response.body() != null)
                myTokenResponse.value = response
            else
                myErrorCodeResponse.value = response.code()
        }
    }
    /*
    fun devregister(
        email: String,
        password: String,
        fullname: String,
        role: String,
        organizationid: Int
    ) {
        viewModelScope.launch {
            val response = repository.devregister(email, password, fullname, role, organizationid)
            myStringResponse.value = response
        }
    }
    LEGACY
     */
    /*
    fun delete(requesteduser: Users) {
        viewModelScope.launch {
            val response = repository.delete(
                com.example.template.functions.data_manipulation.deletionrequesteduser.email,
                com.example.template.functions.data_manipulation.deletionrequesteduser.password_hash,
                com.example.template.functions.data_manipulation.deletionrequesteduser.fullname,
                com.example.template.functions.data_manipulation.deletionrequesteduser.role,
                com.example.template.functions.data_manipulation.deletionrequesteduser.organization_id
            )
            myStringResponse.value = response
        }
    }
    LEGACY
     */
    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            val response = repository.login(email, password)
            if (response.body() != null)
                myTokenResponse.value = response
            else
                myErrorCodeResponse.value = response.code()
        }
    }
    fun check() {
        viewModelScope.launch {
            val response = repository.check()
            if (response.code() == 200)
				myUnitResponse.value = response // not the body,
												// because Unit is a Unit no matter what,
												// I'm just not really sure it will
												// work properly if I don't save the whole
												// response class
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
				// myErrorCodeResponse.value = 200
			} else
				myErrorCodeResponse.value = response.code()
		}
	}
	fun create(
		email: String,
		password: String,
		firstname: String,
		secondname: String
	) {
		viewModelScope.launch {
			val response = repository.create(email, password, firstname, secondname)
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
				myString.value = "SUCCESS" // response.body()
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
    /*
    fun getRole() {
        viewModelScope.launch {
            val response = repository.getRole()
            myStringResponse.value = response
        }
    }
    LEGACY
     */
}