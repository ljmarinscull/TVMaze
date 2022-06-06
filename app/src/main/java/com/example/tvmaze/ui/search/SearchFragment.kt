package com.example.tvmaze.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvmaze.R
import com.example.tvmaze.data.model.HistorySearch
import com.example.tvmaze.data.model.Show
import com.example.tvmaze.databinding.FragmentSearchBinding
import com.example.tvmaze.ui.search.adapters.HistorySearchAdapter
import com.example.tvmaze.ui.search.adapters.SearchAdapter
import com.example.tvmaze.utils.GridSpacingItemDecoration
import com.example.tvmaze.utils.Resource
import com.example.tvmaze.utils.afterTextChanged
import com.example.tvmaze.utils.visible


class SearchFragment : Fragment() {

    private lateinit var mHistorySearchAdapter: HistorySearchAdapter
    private lateinit var mAdapter: SearchAdapter
    private val viewModel: SearchViewModel by activityViewModels()

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        viewModel.getRecentSearches()
    }

    private fun setupViews() = with(binding){

        viewModel.progressBarVisible.observe(viewLifecycleOwner){
            progressBar.visible = it
        }

        historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        mHistorySearchAdapter = HistorySearchAdapter(::onHistorySearchItemClick,::removeHistorySearch)
        historyRecyclerView.adapter = mHistorySearchAdapter

        val historySearchItemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        historySearchItemDecorator.setDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.vertical_divider,
                null
            )!!
        )
        historyRecyclerView.addItemDecoration(
            historySearchItemDecorator
        )

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL,false)
        mAdapter = SearchAdapter(::onClickListener)
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(
            GridSpacingItemDecoration(2,
                resources.getDimensionPixelSize(R.dimen.recycler_item_width)
            )
        )
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.vertical_divider,
                null
            )!!
        )
        recyclerView.addItemDecoration(
            itemDecorator
        )

        search.afterTextChanged {
            if (it.isNotEmpty()){
                viewModel.searchTvShowByName(it)
                notFoundContainer.visible = false
            } else {
                viewModel.getRecentSearches()
                historyRecyclerViewContainer.visible = true
                recyclerView.visible = false
            }
            hideKeyboard()
        }

        clear.setOnClickListener { search.setText("") }

        viewModel.searchedShows.observe(viewLifecycleOwner){
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    if(it.data.isNotEmpty()){
                        historyRecyclerViewContainer.visible = false
                        recyclerView.visible = true
                        mAdapter.mDataSet = it.data
                    } else {
                        notFoundContainer.visible = true
                    }
                }
                is Resource.Error -> {
                    println(it)
                }
            }
        }

        viewModel.historySearches.observe(viewLifecycleOwner){
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    if(it.data.isNotEmpty()){
                        notRecentSearch.visible = false
                        historyRecyclerViewContainer.visible = true
                        mHistorySearchAdapter.mDataSet = it.data.toMutableList()
                    } else {
                        notRecentSearch.visible = true
                        historyRecyclerViewContainer.visible = false
                    }
                }
                is Resource.Error -> {

                }
            }
        }
    }

    private fun onHistorySearchItemClick(search: String) {
        binding.search.setText(search)
    }

    private fun removeHistorySearch(search: HistorySearch){
        viewModel.removeHistorySearch(search)
        val result = mHistorySearchAdapter.remove(search)

        if (result == 0){
            binding.notRecentSearch.visible = true
            binding.historyRecyclerViewContainer.visible = false
        }
    }

    private fun onClickListener(show: Show){
        viewModel.addHistorySearch(HistorySearch(show.id.toLong(), show.name))
        val action = SearchFragmentDirections.navigationSearchToShowDetails(show)
        findNavController().navigate(action)
    }

    private fun hideKeyboard(){
        if(context != null){
            val imm: InputMethodManager? = requireContext().getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view!!.windowToken, 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}