package com.gavril.midapps.friend_app.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gavril.midapps.R
import com.gavril.midapps.databinding.ActivityAddDetailFriendBinding
import com.gavril.midapps.friend_app.database.entity.FriendEntity
import com.gavril.midapps.friend_app.ui.viewmodel.FriendVMFactory
import com.gavril.midapps.friend_app.ui.viewmodel.FriendViewModel
import kotlinx.coroutines.launch

class AddDetailFriendActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddDetailFriendBinding
    private lateinit var viewModel: FriendViewModel
    private lateinit var oldFriend: FriendEntity
    private var idFriend: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddDetailFriendBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO
        )
        val viewModelFactory = FriendVMFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[FriendViewModel::class.java]
        initView()
        binding.btnAdd.setOnClickListener {
            if (idFriend == 0){
                addData()
            } else {
                updateData()
            }
        }
        binding.btnEdit.setOnClickListener {
            binding.tvTitle.text = "UPDATE FRIEND"
            binding.etName.isEnabled = true
            binding.etSchool.isEnabled = true
            binding.etHobby.isEnabled = true
            binding.btnAdd.text = "Update"
            binding.btnAdd.isVisible = true
            binding.btnEdit.isVisible = false
        }
    }
    private fun initView() {
        idFriend = intent.getIntExtra("id", 0)
        if (idFriend == 0) return
        binding.tvTitle.text = "Your Friend Profile"
        binding.etName.isEnabled = false
        binding.etSchool.isEnabled = false
        binding.etHobby.isEnabled = false
        binding.btnAdd.isVisible = false
        binding.btnEdit.isVisible = true
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.getFriendsById(idFriend).collect {friend ->
                        binding.etName.editText?.setText(friend.name)
                        binding.etSchool.editText?.setText(friend.school)
                        binding.etHobby.editText?.setText(friend.hobby)
                        oldFriend = friend
                    }
                }
            }
        }
    }
    private fun updateData() {
        val name = binding.etName.editText?.text.toString().trim()
        val school = binding.etSchool.editText?.text.toString().trim()
        val hobby = binding.etHobby.editText?.text.toString().trim()

        if (name.isEmpty() || school.isEmpty() || hobby.isEmpty()) {
            Toast.makeText(this, "Please fill the blank form", Toast.LENGTH_SHORT).show()
            return
        }

        if (name == oldFriend.name && school == oldFriend.school && hobby == oldFriend.hobby) {
            Toast.makeText(this, "There is no change", Toast.LENGTH_SHORT).show()
            return
        }

        val data = oldFriend.copy(name = name, school = school, hobby = hobby).apply {
            id = idFriend
        }
        lifecycleScope.launch {
            viewModel.insertFriend(data)
        }
        finish()
    }
    private fun addData() {
        val name = binding.etName.editText?.text.toString().trim()
        val school = binding.etSchool.editText?.text.toString().trim()
        val hobby = binding.etHobby.editText?.text.toString().trim()

        if (name.isEmpty() || school.isEmpty() || hobby.isEmpty()) {
            Toast.makeText(this, "Please fill the blank form", Toast.LENGTH_SHORT).show()
            return
        }

        val data = FriendEntity(name, school, hobby)
        lifecycleScope.launch {
            viewModel.insertFriend(data)
        }
        finish()
    }
}