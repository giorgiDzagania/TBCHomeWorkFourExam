package com.exercise.tbchomeworkfourexam.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.exercise.tbchomeworkfourexam.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) :ViewModel() {
    private val _users: MutableStateFlow<List<UserData>> = MutableStateFlow(emptyList())
    val users: StateFlow<List<UserData>> = _users.asStateFlow()

    private var searchDataList = mutableListOf<UserData>()

    @SuppressLint("SuspiciousIndentation")
    fun getUsers() {
        viewModelScope.launch {
            try {
                val user = repository.getUserData()
                    user.forEach { it ->
                    addUser(it)
                }
            } catch (e: Exception) {
               e.toString()
            }
        }
    }

    fun addUser(item: UserData) {
        _users.value = _users.value + item
        searchDataList.add(item)
    }

    fun filterSearch(filterText: String?) {
        Log.d("filterSearch","filter")
        viewModelScope.launch {
            if (filterText.isNullOrBlank()) {
                _users.value = searchDataList
            }else{
                Log.d("filterSearch","filteradd")
                _users.value = searchDataList.filter {userData ->
                    userData.owner.contains(filterText.toString(),true)
                }
            }
        }
    }

}

class ViewModelFactory(private val repository: HomeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}