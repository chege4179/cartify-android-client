package com.peterchege.cartify.repositories

import android.content.SharedPreferences
import com.peterchege.cartify.models.User
import com.peterchege.cartify.util.Constants
import javax.inject.Inject

class UserRepository @Inject constructor(
  private val sharedPreferences: SharedPreferences
) {
    fun getCurrentUser(): User?{
        val id  = sharedPreferences.getString(Constants.USER_ID,null)
        val fullname = sharedPreferences.getString(Constants.USER_FULL_NAME,null)
        val email = sharedPreferences.getString(Constants.USER_EMAIL_ADDRESS,null)
        val phoneNumber = sharedPreferences.getString(Constants.USER_PHONE_NUMBER,null)
        val password = sharedPreferences.getString(Constants.USER_PASSWORD,null)
        val address = sharedPreferences.getString(Constants.USER_ADDRESS,null)
        if (id == null){
            return null
        }else{
            return User(
                _id =  id,
                fullname = fullname ?: "",
                email = email?: "",
                phoneNumber = phoneNumber ?: "",
                password = password ?: "",
                address = address?: ""
            )
        }
    }
    fun saveUser(user:User){
        val userInfoEditor = sharedPreferences.edit()
        userInfoEditor.apply{
            putString(Constants.USER_ID,user._id)
            putString(Constants.USER_FULL_NAME,user.fullname)
            putString(Constants.USER_EMAIL_ADDRESS,user.email)
            putString(Constants.USER_PHONE_NUMBER,user.phoneNumber)
            putString(Constants.USER_PASSWORD,user.password)
            putString(Constants.USER_ADDRESS,user.address)
            apply()
        }
    }
    fun logoutUser(){
        sharedPreferences.edit().remove(Constants.USER_ID).commit()
        sharedPreferences.edit().remove(Constants.USER_FULL_NAME).commit()
        sharedPreferences.edit().remove(Constants.USER_EMAIL_ADDRESS).commit()
        sharedPreferences.edit().remove(Constants.USER_PHONE_NUMBER).commit()
        sharedPreferences.edit().remove(Constants.USER_ADDRESS).commit()
        sharedPreferences.edit().remove(Constants.USER_PASSWORD).commit()
    }
}