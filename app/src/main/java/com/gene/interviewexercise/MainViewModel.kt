package com.gene.interviewexercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gene.interviewexercise.networkservice.NetworkService
import com.gene.interviewexercise.networkservice.NetworkServiceProvider
import com.gene.interviewexercise.networkservice.UrlItem
import kotlinx.coroutines.launch

class MainViewModel(
    private val service: NetworkService = NetworkServiceProvider.provideNetworkService()
) : ViewModel() {

    private val _urls: MutableLiveData<List<UrlItem>> = MutableLiveData()
    val urls: LiveData<List<UrlItem>> get() = _urls

    init {
        viewModelScope.launch {
            val result = service.getWebUrls()
            _urls.value = result.items
        }
    }
}
