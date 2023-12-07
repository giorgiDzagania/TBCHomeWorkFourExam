package com.exercise.tbchomeworkfourexam.fragments


import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.exercise.tbchomeworkfourexam.R
import com.exercise.tbchomeworkfourexam.adapter.HomeAdapter
import com.exercise.tbchomeworkfourexam.databinding.FragmentHomeBinding
import com.exercise.tbchomeworkfourexam.viewModel.HomeRepository
import com.exercise.tbchomeworkfourexam.viewModel.HomeViewModel
import com.exercise.tbchomeworkfourexam.viewModel.ViewModelFactory
import kotlinx.coroutines.launch

class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate)  {
    private val homeViewModel: HomeViewModel by viewModels() {
        ViewModelFactory(HomeRepository())
    }
    private lateinit var homeAdapter: HomeAdapter

    override fun initialData() {
        getAllUsers()
    }

    override fun setUp() {
        prepareRecyclerView()
        observeViewModel()
        btnSearch()
        btnFilterEffect()
    }

    private fun getAllUsers(){
        homeViewModel.getUsers()
    }

    private fun prepareRecyclerView(){
        homeAdapter = HomeAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter = homeAdapter
        }
    }

    private fun observeViewModel(){
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.users.collect { userDataList ->
                homeAdapter.submitList(userDataList)
            }
        }
    }

    private fun btnSearch() {
        binding.btnSearch.doOnTextChanged { text, _, _, _ ->
            homeViewModel.filterSearch(text.toString())
        }
    }

    private fun btnFilterEffect(){
        var count = false
        binding.btnFilter.setOnClickListener {
            if (!count){
                val drawable = ContextCompat.getDrawable(requireContext(),R.drawable.yellow_backgroun_accent)
                binding.linearLayout.background = drawable
            }else{
                binding.linearLayout.background = null
            }
            count = !count
        }
    }

}