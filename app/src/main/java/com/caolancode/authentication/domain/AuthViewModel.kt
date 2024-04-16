package com.caolancode.authentication.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {

    private val _passwordVisibility = MutableStateFlow(false)
    val passwordVisibility = _passwordVisibility.asStateFlow()
    private val _auth = MutableStateFlow(FirebaseAuth.getInstance())
    val auth = _auth.asStateFlow()
    private val _isSignedIn = MutableStateFlow(false)
    val isSignedIn = _isSignedIn.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun setPasswordVisibility() {
        _passwordVisibility.value = !_passwordVisibility.value
    }

    fun setAuth(value: FirebaseAuth) {
        _auth.value = value
    }

    fun createAccount(email: String, password: String) {
        viewModelScope.launch {
            val task = auth.value.createUserWithEmailAndPassword(email, password)
            task.addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("TAG", "createAccount:success")
                    _isSignedIn.value = true
                    updateErrorMessage(null)
                } else {
                    Log.w("TAG", "createAccount:failure", task.exception)
                    _errorMessage.value = it.exception?.message
                }
            }
        }
    }

    fun signInToAccount(email: String, password: String) {
        viewModelScope.launch {
            val task = auth.value.signInWithEmailAndPassword(email, password)
            task.addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    _isSignedIn.value = true
                    updateErrorMessage(null)
                } else {
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    updateErrorMessage(it.exception?.message)
                }
            }
        }
    }

    fun signOutOfAccount() {
        viewModelScope.launch {
            auth.value.signOut()
            _isSignedIn.value = false
        }
    }

    fun updateErrorMessage(value: String?) {
        _errorMessage.value = value
    }
}