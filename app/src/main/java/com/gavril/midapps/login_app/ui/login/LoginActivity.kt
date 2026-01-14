package com.gavril.midapps.login_app.ui.login

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.base.activity.CoreActivity
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.openActivity
import com.gavril.midapps.R
import com.gavril.midapps.databinding.ActivityLoginBinding
import com.gavril.midapps.login_app.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : CoreActivity<ActivityLoginBinding, AuthViewModel>(R.layout.activity_login) {
    var inputUsername = ""
    var inputPassword = ""
    @Inject
    lateinit var session: CoreSession
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO
        )
        binding.activity = this
        binding.btnLogin.setOnClickListener(this)
        loadingDialog.show("Check Status!")
        if (session.getInt(ID) != 0){
            openActivity<HomeActivity>()
            finish()
        }
        loadingDialog.dismiss()
        observe()
    }
    private fun observe(){
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.apiResponse.collect {
                        if (it.status == ApiStatus.LOADING){
                            loadingDialog.show("Login")
                        } else{
                            loadingDialog.dismiss()
                        }
                        if (it.status == ApiStatus.SUCCESS){
                            openActivity<HomeActivity>()
                            finish()
                        }
                    }
                }
            }
        }
    }
    private fun validateLogin(){
        if (inputUsername.isEmpty()){
            binding.etUsername.error ="Isi Username"
        }
        if (inputPassword.isEmpty()){
            binding.etPassword.error ="Isi Password"
        }
        viewModel.login(inputUsername, inputPassword)
    }
    override fun onClick(v: View?){
        when(v){
            binding.btnLogin -> validateLogin()
        }
    }
    companion object {
        const val ID = "id"
        const val FIRST_NAME = "full_name"
        const val LAST_NAME = "last_name"
        const val EMAIL = "email"
        const val USERNAME = "username"
        const val GENDER = "gender"
        const val IMAGE = "image"
        const val PASS = "password"

        const val FULL_NAME = "full_name"
        const val PHONE = "phone"

    }
}