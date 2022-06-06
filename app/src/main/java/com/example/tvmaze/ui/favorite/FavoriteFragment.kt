package com.example.tvmaze.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvmaze.R
import com.example.tvmaze.data.model.ShowFavorite
import com.example.tvmaze.databinding.FragmentFavoriteBinding
import com.example.tvmaze.utils.Resource
import com.example.tvmaze.utils.visible

class FavoriteFragment : Fragment() {

    private lateinit var mAdapter: ShowFavoriteAdapter
    private val viewModel: FavoriteViewModel by activityViewModels()

    private var _binding: FragmentFavoriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        viewModel.loadFavorites()
    }

    private fun setupViews() = with(binding){

        viewModel.progressBarVisible.observe(viewLifecycleOwner){
            progressBar.visible = it
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = ShowFavoriteAdapter(::onClickListener,::removeFromFavorite)
        recyclerView.adapter = mAdapter

        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.vertical_divider_32dp,
                null
            )!!
        )
        recyclerView.addItemDecoration(
            itemDecorator
        )

        viewModel.favorites.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    progressBar.visible = true
                }
                is Resource.Success -> {
                    progressBar.visible = false
                    if(it.data.isNotEmpty()) {
                        emptyContainer.visible = false
                        recyclerView.visible = true
                        mAdapter.mDataSet = it.data.toMutableList()
                    } else {
                        order.isEnabled = false
                    }
                }
                is Resource.Error -> {
                    progressBar.visible = false
                    println(it.exception.localizedMessage)
                }
            }
        }

        order.setOnClickListener {
            if (!viewModel.isOrdered()){
                viewModel.setOrdered()
            } else if (viewModel.isOrdered()){
                Toast.makeText(requireContext(),resources.getString(R.string.result_already_ordered),Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onClickListener(showFavorite: ShowFavorite){
        val action = FavoriteFragmentDirections.navigationFavoriteToShowDetails(showFavorite.show)
        findNavController().navigate(action)
    }

    private fun removeFromFavorite(showFavorite: ShowFavorite){
        val result = mAdapter.remove(showFavorite)
        if (result == 0) {
            binding.emptyContainer.visible = true
            binding.order.isEnabled = false
        }
        viewModel.removeFromFavorite(showFavorite)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}