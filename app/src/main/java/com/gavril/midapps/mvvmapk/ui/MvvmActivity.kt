package com.gavril.midapps.mvvmapk.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.crocodic.core.base.activity.CoreActivity
import com.crocodic.core.extension.openActivity
import com.gavril.midapps.R
import com.gavril.midapps.databinding.ActivityMvvmBinding
//import com.example.mvvm.mvvmapk.ui.viewmodel.Factory
import com.gavril.midapps.mvvmapk.ui.viewmodel.MvvmViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MvvmActivity : CoreActivity<ActivityMvvmBinding, MvvmViewModel>(R.layout.activity_mvvm) {
//    private val viewModel: MvvmViewModel by viewModels()
//    private lateinit var binding: ActivityMvvmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        binding = DataBindingUtil.setContentView(this,R.layout.activity_mvvm)
//        setContentView(R.layout.activity_mvvm)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        val session = PrefsMiddleApps(this)
//        val factory = Factory(session)

//        viewModel = ViewModelProvider(this)[MvvmViewModel::class.java]
//        viewModel = ViewModelProvider(this, factory)[MvvmViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.btnChangeNoMvvm.setOnClickListener {
            openActivity<NoMvvmActivity>()
        }
    }
}