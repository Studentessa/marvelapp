package com.mmartinez.feature_character_list.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmartinez.data.domain.CharacterDModel
import com.mmartinez.feature_character_list.R
import com.mmartinez.feature_character_list.base.gone
import com.mmartinez.feature_character_list.base.observe
import com.mmartinez.feature_character_list.base.visible
import com.mmartinez.feature_character_list.databinding.FragmentListCharacterBinding
import com.mmartinez.feature_character_list.view.adapter.CharacterListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterListFragment : Fragment() {
    private val stateObserver = Observer(::onStateChanged)
    private lateinit var binding: FragmentListCharacterBinding
    private val viewModel: CharacterListViewModel by viewModels()

    private val characterList: ArrayList<CharacterDModel> = ArrayList()
    private val characterListAdapter by lazy {
        CharacterListAdapter(characterList){
            onCharacterSelected(it)
        }
    }

    private fun onCharacterSelected(characterDModel: CharacterDModel) {
        activity?.let {
            val directions = CharacterListFragmentDirections.jumpToDetailFragment()
            directions.idCharacter = characterDModel.id
            findNavController().navigate(directions)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, stateObserver)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListCharacterBinding.inflate(inflater, container, false)
        activity?.title  = getString(R.string.fragment_lit_title)
        val view = binding.root
        initView()
        return view
    }

    private fun initView() {
        val lyManager = GridLayoutManager(requireActivity(), 2)
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                viewModel.notifiedLastVisible.value = lyManager.findLastVisibleItemPosition()
            }
        })

        binding.recyclerView.apply {
            this.layoutManager = lyManager
            adapter = characterListAdapter
        }
    }

    private fun onStateChanged(state: CharacterListViewModelState) {
        when (state){
            is CharacterListViewModelState.Success -> displayContent(state)
            is CharacterListViewModelState.LoadingList -> displayLoading()
            is CharacterListViewModelState.Error -> displayError()
            is CharacterListViewModelState.Empty -> displayEmptyView()
        }
    }

    private fun displayEmptyView() {

        binding.emptyView.emptyViewParent.visible()
        binding.errorView.errorViewParent.gone()
    }

    private fun displayError() {
        binding.loading.loadingView.gone()
        binding.emptyView.emptyViewParent.gone()
        binding.errorView.errorViewParent.visible()
    }

    private fun displayLoading() {
        binding.loading.loadingView.visible()
        binding.emptyView.emptyViewParent.gone()
        binding.errorView.errorViewParent.gone()
    }

    private fun displayContent(state: CharacterListViewModelState.Success) {
        binding.loading.loadingView.gone()
        binding.emptyView.emptyViewParent.gone()
        binding.errorView.errorViewParent.gone()
        binding.recyclerView.visible()
        characterList.addAll(characterList.lastIndex +1, state.characterListDModel.list)
        characterListAdapter.notifyDataSetChanged()
    }

}