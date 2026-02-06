package com.gavril.midapps.login_app.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LOGGER
import com.crocodic.core.base.activity.CoreActivity
import com.crocodic.core.base.adapter.ReactiveListAdapter
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.tos
import com.gavril.midapps.R
import com.gavril.midapps.databinding.ActivityHomeBinding
import com.gavril.midapps.databinding.ItemProductBinding
import com.gavril.midapps.login_app.model.DataProduct
import com.gavril.midapps.login_app.ui.SettingActivity
import com.gavril.midapps.login_app.ui.login.AuthViewModel
import com.gavril.midapps.login_app.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : CoreActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home) {
    var inputKeyword = ""
    private val adapterCore by lazy {
        ReactiveListAdapter<ItemProductBinding, DataProduct>(R.layout.item_product).initItem { position, data -> tos("$position -> {$data.title}") }
    }
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
        binding.rvShowData.adapter = adapterCore
        val desc = "Hello," + session.getString(LoginActivity.FIRST_NAME) + " " + session.getString(LoginActivity.LAST_NAME)
        binding.tvOutput.text = desc
        binding.btnSettings.setOnClickListener (this)
        binding.etSearch.editText?.doOnTextChanged { text, start, before, count ->
            val keyword = text.toString().trim()
            viewModel.getProduct(keyword)
        }
        observe()
        viewModel.getProduct()
    }
    private fun observe(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.product.collect{data ->
                        adapterCore.submitList(data)
                    }
                }
            }
        }
    }
    override fun onClick(v: View?){
        when(v){
            binding.btnSettings -> openActivity<SettingActivity>()
        }
    }

    private fun setting() {
        /*session.clearAll()
        session.setValue(CoreSession.PREF_UID, 0)
        finishAffinity()*/

    }
}