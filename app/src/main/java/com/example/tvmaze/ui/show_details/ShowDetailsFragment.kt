package com.example.tvmaze.ui.show_details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.tvmaze.R
import com.example.tvmaze.data.model.Episode
import com.example.tvmaze.databinding.FragmentShowDetailsBinding
import com.example.tvmaze.utils.Resource
import kotlinx.coroutines.runBlocking

const val ARG_SHOW = "show"

/**
 * A simple [Fragment] subclass that show the show details.
 */
class ShowDetailsFragment : Fragment() {

    private var _binding: FragmentShowDetailsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ShowDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.setCurrentShow(it.getParcelable(ARG_SHOW)!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runBlocking {
            viewModel.checkIfItIsFavorite()
        }
        setupViews()
        viewModel.getShowsEpisodes()
    }


    private fun setupViews() = with(binding){

        viewModel.progressBarVisible.observe(viewLifecycleOwner){
          //  progressBar.visible = it
        }

        val currentShow = viewModel.getCurrentShow()
        name.text = currentShow.name
        days.text = currentShow.days
        time.text = currentShow.time
        gender.text =  currentShow.genres

        val htmlAsSpanned = HtmlCompat.fromHtml(currentShow.summary, 0)
        summary.text = htmlAsSpanned

        Glide.with(requireContext())
            .load(currentShow.poster ?: resources.getDrawable(R.drawable.res_no_image, null))
            .centerInside()
            .into(image)

        val setting = setupFavoriteButton()

        favorite.text = setting.first
        favorite.icon = setting.second

        favorite.setOnClickListener {
            viewModel.setFavoriteStateToCurrentShow()
            val values = setupFavoriteButton()
            if(currentShow.favorite) {
                viewModel.markAsFavorite()
            } else {
                viewModel.removeFromFavorite()
            }
            favorite.text = values.first
            favorite.icon = values.second
        }

        viewModel.episodesBySeason.observe(viewLifecycleOwner){
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    if(it.data.isNotEmpty()){
                        setupEpisodesBySeason(it.data)
                    }
                }
                is Resource.Error -> {
                    println(it.exception.localizedMessage)
                }
            }
        }

        backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupEpisodesBySeason(data: Map<Int, List<Episode>>) {
        data.forEach { (key, value) ->
            val customView = CustomSeasonEpisodeView(requireContext(), key, value,::onClickEpisodeListener)
            binding.episodesContainer.addView(customView)
        }
    }

    private fun onClickEpisodeListener(episode: Episode){
        val action = ShowDetailsFragmentDirections.navigationShowDetailsToEpisodeDetails(episode)
        findNavController().navigate(action)
    }

    private fun setupFavoriteButton(): Pair<String,Drawable>{
        val drawable: Drawable?
        val text: String

        if(viewModel.isShowFavorite()) {
            drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_fill)
            text = resources.getString(R.string.remove_from_fav)
            viewModel.markAsFavorite()
        } else {
            drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_border)
            text = resources.getString(R.string.mark_as_fav)
            viewModel.removeFromFavorite()
        }

        return Pair(text, drawable!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}