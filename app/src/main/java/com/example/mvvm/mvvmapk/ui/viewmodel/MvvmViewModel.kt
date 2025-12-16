package com.example.mvvm.mvvmapk.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MvvmViewModel : ViewModel() {

    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count

    fun increment(){
        _count.value += 1
    }

    fun decrement(){
        _count.value -= 1
    }}