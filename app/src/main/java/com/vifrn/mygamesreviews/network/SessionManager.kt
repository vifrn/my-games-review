package com.vifrn.mygamesreviews.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vifrn.mygamesreviews.R
import com.vifrn.mygamesreviews.network.auth.AuthResponse
import com.vifrn.mygamesreviews.network.auth.TwitchAuthApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

/**
 * Session manager to save and fetch data from SharedPreferences
 */
class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val dateFormat = SimpleDateFormat(DATE_PATTERN)

    private val _newTokenReady = MutableLiveData<TokenStatus>()
    val newTokenReady : LiveData<TokenStatus>
        get () = _newTokenReady

    companion object {
        private const val TAG = "SessionsManager"

        const val TOKEN = "token"
        const val TOKEN_EXPIRATION = "token_expiration"

        const val DATE_PATTERN = "dd/MM/yyyy HH:mm"
    }

    fun saveAuthToken(token: String, expiration : Int) {
        val editor = prefs.edit()

        val c = Calendar.getInstance()
        c.time = Date()
        c.add(Calendar.SECOND, expiration)
        val resultDate = c.time

        Log.d(TAG, "resultDate: ${dateFormat.format(resultDate)}")

        editor.putString(TOKEN, "Bearer $token")
        editor.putString(TOKEN_EXPIRATION, dateFormat.format(resultDate))
        editor.apply()
    }

    fun hasValidToken() : Boolean {
        if (fetchAuthToken() == null) return false

        val exp = prefs.getString(TOKEN_EXPIRATION, null) ?: return false

        val expirationDate = dateFormat.parse(exp)
        val today = Date()

        if (expirationDate != null) {
            return expirationDate.after(today)
        }

        return false
    }

    fun getNewToken () {
        _newTokenReady.value = TokenStatus.REQUESTING
        TwitchAuthApi.retrofitService.auth().enqueue( object: Callback<AuthResponse> {
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                _newTokenReady.value = TokenStatus.ERROR
            }

            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    saveAuthToken(response.body()!!.accessToken, response.body()!!.expiresIn)
                    _newTokenReady.value = TokenStatus.READY
                } else {
                    Log.d(TAG, "API returned code response: ${response.code()}")
                    _newTokenReady.value = TokenStatus.ERROR
                }

            }
        })
    }

    fun clearTokenStatus () {
        _newTokenReady.value = TokenStatus.IDLE
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(TOKEN, null)
    }
}

enum class TokenStatus {READY, REQUESTING, IDLE, ERROR}