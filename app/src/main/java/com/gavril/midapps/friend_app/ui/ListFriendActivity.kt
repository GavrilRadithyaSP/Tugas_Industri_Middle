package com.gavril.midapps.friend_app.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.base.activity.CoreActivity
import com.crocodic.core.extension.openActivity
import com.gavril.midapps.R
import com.gavril.midapps.databinding.ActivityListFriendBinding
import com.gavril.midapps.friend_app.adapter.FriendAdapter
import com.gavril.midapps.friend_app.database.MyDatabase
import com.gavril.midapps.friend_app.database.entity.FriendEntity
import com.gavril.midapps.friend_app.ui.viewmodel.FriendViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFriendActivity : CoreActivity<ActivityListFriendBinding, FriendViewModel>(R.layout.activity_list_friend) {
    private lateinit var adapter: FriendAdapter
    private lateinit var database: MyDatabase
    private val friendList = ArrayList<FriendEntity>()
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
        /*val viewModelFactory = FriendVMFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[FriendViewModel::class.java]*/
        binding.lifecycleOwner = this
        adapter = FriendAdapter(this,
            { positon, data -> openActivity<AddDetailFriendActivity> { putExtra("id", data.id) }/*val destination = Intent(this, AddDetailFriendActivity::class.java).apply { putExtra("id", data.id) }
                startActivity(destination)*/ },
            { position, data -> deleteFriend(data) }
        )
        binding.rvFriend.adapter = adapter
        viewModel.getFriends()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.friends.collect {friend ->
                        friendList.clear()
                        friendList.addAll(friend)
                        adapter.setData(friendList)                    }
                }
            }
        }
        binding.btnAdd.setOnClickListener {
            /*val intent = Intent(this, AddDetailFriendActivity::class.java)
            startActivity(intent)*/
            openActivity<AddDetailFriendActivity>()
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