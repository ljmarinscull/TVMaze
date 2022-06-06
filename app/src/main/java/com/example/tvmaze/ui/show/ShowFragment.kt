package com.example.tvmaze.ui.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvmaze.R
import com.example.tvmaze.data.model.Show
import com.example.tvmaze.databinding.FragmentShowBinding
import com.example.tvmaze.utils.GridSpacingItemDecoration
import com.example.tvmaze.utils.Resource
import com.example.tvmaze.utils.visible

class ShowFragment : Fragment() {

    private lateinit var mAdapter: ShowAdapter
    private var _binding: FragmentShowBinding? = null
    private var isLoading = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: ShowViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentShowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupScrollListener()
        viewModel.loadItems(-1)
    }

    private fun setupViews() = with(binding){

        viewModel.progressBarVisible.observe(viewLifecycleOwner){
            progressBar.visible = it
        }

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL,false)
        mAdapter = ShowAdapter(::onClickListener)
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

        viewModel.shows.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    isLoading = false
                    mAdapter.addItems(it.data)
                    recyclerView.visibility = View.VISIBLE
                }
                is Resource.Error -> {

                }
            }
        }
    }

    private fun setupScrollListener() = with(binding) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mAdapter.itemCount - 1) {
                        viewModel.loadItems()
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun onClickListener(show: Show){
        val action = ShowFragmentDirections.navigationHomeToShowDetails(show)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}