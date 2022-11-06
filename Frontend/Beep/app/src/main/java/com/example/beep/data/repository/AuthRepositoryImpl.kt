package com.example.beep.data.repository

import android.content.SharedPreferences
import com.example.beep.data.dto.auth.*
import com.example.beep.network.api.AuthApi
import retrofit2.HttpException

class AuthRepositoryImpl(
        private val api: AuthApi,
        private val prefs: SharedPreferences
) : AuthRepository {

    override suspend fun signUp(phoneNumber: String, password: String, fcmToken: String): AuthResult<Unit> {
        return try {
            api.signUp(
                    request = SignUpRequest(
                            phoneNumber = phoneNumber,
                            password = password,
                            fcmToken = fcmToken
                    )
            )
            login(phoneNumber, password)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun login(phoneNumber: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.login(
                    request = LoginRequest(
                            phoneNumber = phoneNumber,
                            password = password
                    )
            )
            prefs.edit()
                    .putString("jwt", response.token)
                    .apply()
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }
}
