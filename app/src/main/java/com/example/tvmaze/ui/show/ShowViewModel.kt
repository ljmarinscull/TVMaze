package com.example.tvmaze.ui.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvmaze.data.model.Show
import com.example.tvmaze.data.repository.IShowRepository
import com.example.tvmaze.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowViewModel @Inject constructor(
    private val repo: IShowRepository
): ViewModel() {

    private val _progressBarVisible = MutableLiveData<Boolean>()
    val progressBarVisible: LiveData<Boolean> = _progressBarVisible

    private val _shows = MutableLiveData<Resource<List<Show>>>()
    val shows: LiveData<Resource<List<Show>>> = _shows

    private var mCurrentPage: Int = 0

    fun loadItems() {
        _progressBarVisible.value = true
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = repo.getShows(mCurrentPage)
                if((result as Resource.Success).data.isNotEmpty())
                    mCurrentPage++
                _progressBarVisible.postValue(false)
                _shows.postValue(result)
            } catch (e: Exception){
                _progressBarVisible.postValue(false)
                _shows.postValue(Resource.Error(e))
            }
        }
    }
}