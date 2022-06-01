package com.example.tvmaze.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvmaze.data.model.HistorySearch
import com.example.tvmaze.data.model.Show
import com.example.tvmaze.data.repository.IHistorySearchRepository
import com.example.tvmaze.data.repository.IShowRepository
import com.example.tvmaze.utils.Resource
import com.example.tvmaze.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: IShowRepository,
    private val historySearchRepo: IHistorySearchRepository
) : ViewModel() {

    private val _progressBarVisible = MutableLiveData<Boolean>()
    val progressBarVisible: LiveData<Boolean> = _progressBarVisible

    private val _searchedShows = SingleLiveEvent<Resource<List<Show>>>()
    val searchedShows: LiveData<Resource<List<Show>>> = _searchedShows

    private val _historySearches = SingleLiveEvent<Resource<List<HistorySearch>>>()
    val historySearches: LiveData<Resource<List<HistorySearch>>> = _historySearches

    fun searchTvShowByName(name: String){
        _progressBarVisible.value = true
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = repo.getShowByName(name)
                _progressBarVisible.postValue(false)
                _searchedShows.postValue(result)
            } catch (e: Exception){
                _progressBarVisible.postValue(false)
                _searchedShows.postValue(Resource.Error(e))
            }
        }
    }

    fun getRecentSearches() {
        _progressBarVisible.value = true
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = historySearchRepo.getRecentSearches()
                _progressBarVisible.postValue(false)
                _historySearches.postValue(result)
            } catch (e: Exception){
                _progressBarVisible.postValue(false)
                _historySearches.postValue(Resource.Error(e))
            }
        }
    }

    fun removeHistorySearch(search: HistorySearch) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = historySearchRepo.deleteRecentSearch(search)
            } catch (e: Exception){
                _historySearches.postValue(Resource.Error(e))
            }
        }
    }

    fun addHistorySearch(search: HistorySearch) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = historySearchRepo.addRecentSearch(search)
            } catch (e: Exception){
                _historySearches.postValue(Resource.Error(e))
            }
        }
    }

}