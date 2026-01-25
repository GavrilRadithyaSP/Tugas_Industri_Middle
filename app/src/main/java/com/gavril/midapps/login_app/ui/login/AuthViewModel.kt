package com.gavril.midapps.login_app.ui.login

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.gavril.midapps.friend_app.base.BaseViewModel
import com.gavril.midapps.login_app.api.ApiService
import com.gavril.midapps.login_app.api.response.AuthResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val session: CoreSession,
    private val apiService: ApiService
): BaseViewModel() {
    fun login(username: String, password: String) = viewModelScope.launch {
        _apiResponse.emit(ApiResponse().responseLoading())
        ApiObserver.run(
            {apiService.login(username, password)},
            false,
            object : ApiObserver.ModelResponseListener<AuthResponse>{
                override suspend fun onSuccess(response: AuthResponse) {
                    session.setValue(LoginActivity.ID, response.id)
                    session.setValue(LoginActivity.USERNAME, response.username)
                    session.setValue(LoginActivity.PASS, password)
                    session.setValue(LoginActivity.EMAIL, response.email)
                    session.setValue(LoginActivity.FIRST_NAME, response.firstName)
                    session.setValue(LoginActivity.LAST_NAME, response.lastName)
                    session.setValue(LoginActivity.GENDER, response.gender)
                    session.setValue(LoginActivity.IMAGE, response.image)
                    session.setValue(CoreSession.PREF_UID, response.token?:"")
                    _apiResponse.emit(ApiResponse().responseSuccess())
                }

                override suspend fun onError(response: AuthResponse) {
                    _apiResponse.emit(ApiResponse().responseError())
                }
            }
        )
    }
}