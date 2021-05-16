package com.mmartinez.feature_character_detail.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.mmartinez.feature_character_detail.R
import com.mmartinez.feature_character_detail.databinding.FragmentCharacterDetailBinding
import com.mmartinez.feature_character_detail.view.comiclist.ComicListAdapter
import com.mmartinez.feature_character_list.base.gone
import com.mmartinez.feature_character_list.base.observe
import com.mmartinez.feature_character_list.base.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {
    private val stateObserver = Observer(::onStateChanged)
    private val viewModel: CharacterDetailViewModel by viewModels()
    private lateinit var binding: FragmentCharacterDetailBinding

    private val idCharacter by lazy {
        arguments?.let { CharacterDetailFragmentArgs.fromBundle(it).idCharacter }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idCharacter?.let {
            viewModel.selectedCharacterId.value = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, stateObserver)
        (activity as AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun onStateChanged(state: CharacterDetailViewModelState) {
        when (state){
            is CharacterDetailViewModelState.Success -> displayContent(state)
            is CharacterDetailViewModelState.LoadingData -> displayLoading()
            is CharacterDetailViewModelState.Error -> displayError()
            is CharacterDetailViewModelState.Empty -> displayEmptyView()
        }
    }

    private fun displayEmptyView() {
        binding.lyLoading.loadingView.gone()
        binding.layouDetail.gone()
    }

    private fun displayError() {
        binding.lyLoading.loadingView.gone()
        binding.layouDetail.gone()
    }

    private fun displayLoading() {
        binding.lyLoading.loadingView.visible()
        binding.layouDetail.gone()
    }

    private fun displayContent(state: CharacterDetailViewModelState.Success) {
        binding.lyLoading.loadingView.gone()
        binding.layouDetail.visible()
        val character = state.characterListDModel.list[0]
        if(character != null){
            activity?.title  = character.name
            binding.imageView.load(character.imageUrl)
            if(!character.comicList?.list.isNullOrEmpty()){
                binding.recyclerComics.apply {
                    adapter = ComicListAdapter(character.comicList?.list!!)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                }
            }
            binding.characterNameTextView.text = character.name
            binding.descriptionTextView.text =
                if(!character.description.isNullOrEmpty()) character.description
                else getString(R.string.fragment_detail_description_not_available)
        }

    }

}