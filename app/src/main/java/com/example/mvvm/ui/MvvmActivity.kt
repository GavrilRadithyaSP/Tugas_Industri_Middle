package com.example.mvvm.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.R
import com.example.mvvm.databinding.ActivityMvvmBinding
import com.example.mvvm.ui.viewmodel.MvvmViewModel

class MvvmActivity : AppCompatActivity() {

    private lateinit var viewModel: MvvmViewModel
    private lateinit var binding: ActivityMvvmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_mvvm)
//        setContentView(R.layout.activity_mvvm)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[MvvmViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.btnChangeNoMvvm.setOnClickListener {
            val intent = Intent(this, NoMvvmActivity::class.java)
            startActivity(intent)
        }
    }
}