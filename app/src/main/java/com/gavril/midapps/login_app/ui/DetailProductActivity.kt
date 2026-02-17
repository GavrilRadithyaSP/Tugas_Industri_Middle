package com.gavril.midapps.login_app.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.crocodic.core.base.activity.CoreActivity
import com.gavril.midapps.R
import com.gavril.midapps.databinding.ActivityDetailProductBinding
import com.gavril.midapps.login_app.model.DataProduct
import com.gavril.midapps.login_app.ui.home.HomeViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailProductActivity : CoreActivity<ActivityDetailProductBinding, HomeViewModel>(R.layout.activity_detail_product) {
    @Inject
    lateinit var gson: Gson
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val dataIntent = intent.getStringExtra(DATA)
        binding.data = gson.fromJson(dataIntent, DataProduct::class.java)
    }
    companion object{
        const val DATA = "data"
    }
}