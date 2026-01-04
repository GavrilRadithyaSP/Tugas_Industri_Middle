package com.gavril.midapps.friend_app.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gavril.midapps.R
import com.gavril.midapps.databinding.ActivityListFriendBinding
import com.gavril.midapps.friend_app.adapter.FriendAdapter
import com.gavril.midapps.friend_app.database.MyDatabase
import com.gavril.midapps.friend_app.database.entity.FriendEntity
import com.gavril.midapps.friend_app.ui.viewmodel.FriendVMFactory
import com.gavril.midapps.friend_app.ui.viewmodel.FriendViewModel
import kotlinx.coroutines.launch

class ListFriendActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListFriendBinding
    private lateinit var viewModel: FriendViewModel
    private lateinit var adapter: FriendAdapter
    private lateinit var database: MyDatabase
    private val friendList = ArrayList<FriendEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListFriendBinding.inflate(layoutInflater)
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
        adapter = FriendAdapter(this,
            { positon, data -> val destination = Intent(this, AddDetailFriendActivity::class.java).apply { putExtra("id", data.id) }
                startActivity(destination) },
            { position, data -> deleteFriend(data) }
        )
        binding.rvFriend.adapter = adapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.getFriends().collect {friend ->
                        friendList.clear()
                        friendList.addAll(friend)
                        adapter.setData(friendList)                    }
                }
            }
        }
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddDetailFriendActivity::class.java)
            startActivity(intent)
        }
    }
    private fun deleteFriend(data: FriendEntity){
        AlertDialog.Builder(this)
            .setTitle("Delete Friend")
            .setMessage("Are you sure?")
            .setPositiveButton("Yes") { _, _ ->
                lifecycleScope.launch {
                    viewModel.deleteFriend(data)
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}