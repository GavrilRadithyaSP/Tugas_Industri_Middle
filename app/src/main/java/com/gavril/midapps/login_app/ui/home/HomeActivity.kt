package com.gavril.midapps.login_app.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.paging.LOGGER
import com.crocodic.core.base.activity.CoreActivity
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.openActivity
import com.gavril.midapps.R
import com.gavril.midapps.databinding.ActivityHomeBinding
import com.gavril.midapps.login_app.ui.login.AuthViewModel
import com.gavril.midapps.login_app.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : CoreActivity<ActivityHomeBinding, AuthViewModel>(R.layout.activity_home) {
    @Inject
    lateinit var session: CoreSession
    override fun onCreate(savedInstanceState: Bundle?) {
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
        val desc = session.getInt(LoginActivity.ID).toString() + "\n" + session.getString(
            LoginActivity.EMAIL) + "\n" + session.getString(LoginActivity.FIRST_NAME) + "" + session.getString(
            LoginActivity.LAST_NAME)
        binding.tvOutput.text = desc
        binding.btnLogout.setOnClickListener(this)
    }
    override fun onClick(v: View?){
        when(v){
            binding.btnLogout -> logout()
        }
    }

    private fun logout() {
        session.clearAll()
        session.setValue(CoreSession.PREF_UID, 0)
        finishAffinity()
        openActivity<LoginActivity>()
    }
}