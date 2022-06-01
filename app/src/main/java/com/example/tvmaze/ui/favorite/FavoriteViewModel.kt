package com.example.tvmaze.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvmaze.data.model.ShowFavorite
import com.example.tvmaze.data.repository.IShowFavoriteRepository
import com.example.tvmaze.utils.Resource
import com.example.tvmaze.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val showFavoriteRepo: IShowFavoriteRepository,
): ViewModel() {

    private var ordered = false

    private val _progressBarVisible = MutableLiveData<Boolean>()
    val progressBarVisible: LiveData<Boolean> = _progressBarVisible

    private val _favorites = SingleLiveEvent<Resource<List<ShowFavorite>>>()
    val favorites: LiveData<Resource<List<ShowFavorite>>> = _favorites

    fun loadFavorites() {
        _progressBarVisible.value = true
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val data =
                    (showFavoriteRepo.getFavorites() as Resource.Success).data
                val result = if(!ordered)
                    data
                else
                    data.sortedBy { it.show.name }

                _favorites.postValue(Resource.Success(result))
            } catch (e: Exception){
                _progressBarVisible.postValue(false)
                _favorites.postValue(Resource.Error(e))
            }
        }
    }

    fun removeFromFavorite(showFavorite: ShowFavorite) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                showFavoriteRepo.deleteShowFromFavorite(showFavorite)
            } catch (e: Exception){
                println(e.localizedMessage)
            }
        }
    }

    fun setOrdered() {
        ordered = true
        loadFavorites()
    }

    fun isOrdered() = ordered
}