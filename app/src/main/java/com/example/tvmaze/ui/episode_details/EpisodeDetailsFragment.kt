package com.example.tvmaze.ui.episode_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.tvmaze.R
import com.example.tvmaze.data.model.Episode
import com.example.tvmaze.databinding.FragmentEpisodeDetailsBinding

// the fragment initialization parameters, e.g. ARG_EPISODE
private const val ARG_EPISODE = "episode"

/**
 * A simple [Fragment] subclass.
 */
class EpisodeDetailsFragment : Fragment() {

    private var mEpisode: Episode? = null
    private var _binding: FragmentEpisodeDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mEpisode = it.getParcelable(ARG_EPISODE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEpisodeDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() = with(binding){

        name.text = mEpisode?.name.toString()
        season.text = mEpisode?.season.toString()
        episode.text = mEpisode?.number.toString()

        val htmlAsSpanned = HtmlCompat.fromHtml(mEpisode?.summary!!, 0)
        summary.text = htmlAsSpanned

        Glide.with(requireContext())
            .load(mEpisode?.image ?: ResourcesCompat.getDrawable(resources, R.drawable.res_no_image, null))
            .centerInside()
            .into(image)

        backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}