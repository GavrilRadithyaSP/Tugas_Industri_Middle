package com.gavril.midapps.login_app.ui.login

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.base.activity.CoreActivity
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.snacked
import com.gavril.midapps.R
import com.gavril.midapps.databinding.ActivityLoginBinding
import com.gavril.midapps.login_app.ui.SettingActivity
import com.gavril.midapps.login_app.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : CoreActivity<ActivityLoginBinding, AuthViewModel>(R.layout.activity_login) {
    var inputUsername = ""
    var inputPassword = ""
    @Inject
    lateinit var session: CoreSession
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
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
        binding.btnLoginBiometric.setOnClickListener(this)
        binding.btnLoginBiometric.isVisible = session.getBoolean(SettingActivity.BIOMETRIC_STATUS)
        loadingDialog.show("Check Status!")
        if (session.getInt(ID) != 0){
            openActivity<HomeActivity>()
            finish()
        }
        loadingDialog.dismiss()
        observe()
        initBiometric()
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
            binding.btnLoginBiometric -> biometricPrompt.authenticate(promptInfo)
        }
    }
    private fun initBiometric(){
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    binding.root.snacked("Biometric Error: $errString")
                }
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    binding.root.snacked("Biometric Succeeded!")
                    viewModel.login(session.getString(USERNAME), session.getString(PASS))
                }
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    binding.root.snacked("Biometric Failed")
                }
            })
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login")
            .setSubtitle("Login Using Your Biometric Credential")
            .setNegativeButtonText("Use Account Instead")
            .build()
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