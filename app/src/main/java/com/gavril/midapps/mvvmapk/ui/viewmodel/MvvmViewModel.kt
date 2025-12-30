package com.gavril.midapps.mvvmapk.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.gavril.midapps.session.PrefsMiddleApps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MvvmViewModel @Inject constructor(private val prefsMiddleApps: PrefsMiddleApps) : CoreViewModel() {

    private val _count = MutableStateFlow(prefsMiddleApps.getCount())
    val count: StateFlow<Int> = _count

    fun increment(){
        _count.value += 5
        prefsMiddleApps.saveCount(_count.value)
    }

    fun decrement(){
        _count.value -= 5
        prefsMiddleApps.saveCount(_count.value)
    }

    override fun apiLogout() {}

    override fun apiRenewToken() {}
}

//class Factory(private val prefsMiddleApps: PrefsMiddleApps): ViewModelProvider.Factory{
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return MvvmViewModel(prefsMiddleApps) as T
//    }
//}