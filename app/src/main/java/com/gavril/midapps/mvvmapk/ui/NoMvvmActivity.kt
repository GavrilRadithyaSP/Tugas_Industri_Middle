package com.gavril.midapps.mvvmapk.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.crocodic.core.extension.openActivity
import com.gavril.midapps.R
import com.gavril.midapps.databinding.ActivityNoMvvmBinding

class NoMvvmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoMvvmBinding

    private var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_no_mvvm)
//        setContentView(R.layout.activity_no_mvvm)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.lifecycleOwner = this

        binding.tvNoMvvm.text = "$counter"

        binding.btnIncNoMvvm.setOnClickListener {
            counter += 1
            binding.tvNoMvvm.text = "$counter"
        }
        binding.btnDecNoMvvm.setOnClickListener {
            counter -= 1
            binding.tvNoMvvm.text = "$counter"
        }
        binding.btnChangeMvvm.setOnClickListener {
            openActivity<MvvmActivity>()
        }
    }
}