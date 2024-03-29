package com.example.marboles.mvvm.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Level View
data class LevelStatus(val levelNumber: Int, val isUnlocked: Boolean)

class LevelViewModel(context: Context) : ViewModel() {
    private val _levelStatusList = MutableLiveData<List<LevelStatus>>()
    val levelStatusList: LiveData<List<LevelStatus>> get() = _levelStatusList

    init {
        val initialStatusList = List(7) { LevelStatus(it + 1, false) }
        _levelStatusList.value = initialStatusList
    }

    fun unlockLevel(levelNumber: Int) {
        val currentStatusList = _levelStatusList.value.orEmpty().toMutableList()
        if (levelNumber in 1..currentStatusList.size) {
            currentStatusList[levelNumber - 1] = currentStatusList[levelNumber - 1].copy(isUnlocked = true)
            _levelStatusList.value = currentStatusList
        }
    }
}
