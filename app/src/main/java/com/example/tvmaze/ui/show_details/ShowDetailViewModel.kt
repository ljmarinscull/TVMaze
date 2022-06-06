package com.example.tvmaze.ui.show_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvmaze.data.model.Episode
import com.example.tvmaze.data.model.Show
import com.example.tvmaze.data.repository.IShowFavoriteRepository
import com.example.tvmaze.data.repository.IShowRepository
import com.example.tvmaze.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    private val repo: IShowRepository,
    private val favoriteRepo: IShowFavoriteRepository
): ViewModel() {

    private var mCurrentShow: Show? = null
    private val _progressBarVisible = MutableLiveData<Boolean>()
    val progressBarVisible: LiveData<Boolean> = _progressBarVisible

    private val _episodesBySeason = MutableLiveData<Resource<Map<Int,List<Episode>>>>()
    val episodesBySeason: LiveData<Resource<Map<Int,List<Episode>>>> = _episodesBySeason

    fun setCurrentShow(show: Show){
        mCurrentShow = show
    }
    fun getCurrentShow() = mCurrentShow!!

    fun isShowFavorite() = mCurrentShow?.favorite!!

    fun getShowsEpisodes(){
        _progressBarVisible.value = true
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = repo.getShowsEpisodes(mCurrentShow?.id!!)
                val data = (result as Resource.Success).data

                val grouped = orderListByGroupId(data)

                _progressBarVisible.postValue(false)
                _episodesBySeason.postValue(Resource.Success(grouped))
            } catch (e: Exception){
                _progressBarVisible.postValue(false)
                _episodesBySeason.postValue(Resource.Error(e))
            }
        }
    }

    private fun orderListByGroupId(list: List<Episode>): Map<Int, List<Episode>> {
        if(list.isEmpty())
            return emptyMap()

        val groupedByGroupId = list.groupBy(Episode::season)

        return groupedByGroupId
    }

    fun markAsFavorite() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                favoriteRepo.addShowToFavoriteIfNotExist(mCurrentShow!!)
            } catch (e: Exception){
                println(e.localizedMessage)
            }
        }
    }

    fun removeFromFavorite() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                favoriteRepo.deleteShowFromFavoriteIfExist(mCurrentShow!!)
            } catch (e: Exception){
                println(e.localizedMessage)
            }
        }
    }

    suspend fun checkIfItIsFavorite() : Boolean {
           try {
               val result = favoriteRepo.checkIfItIsFavorite(mCurrentShow!!)
               return result
            } catch (e: Exception){
                println(e.localizedMessage)
                return false
            }
    }

    fun setFavoriteStateToCurrentShow() {
        mCurrentShow?.favorite = !mCurrentShow?.favorite!!
    }
}