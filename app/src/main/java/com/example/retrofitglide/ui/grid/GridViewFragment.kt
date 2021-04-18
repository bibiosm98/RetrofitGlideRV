package com.example.retrofitglide.ui.grid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitglide.databinding.GridViewFragmentBinding

class GridViewFragment : Fragment() {
    private lateinit var viewModel: GridViewViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(GridViewViewModel::class.java)

        val binding = GridViewFragmentBinding.inflate(inflater)

        binding.viewModel = viewModel
//        viewModel.comic.observe(viewLifecycleOwner, Observer {
////            binding.textHome.text = it.thumbnail.path
//        })
        binding.lifecycleOwner = this

        binding.imagesGrid.adapter = PhotoGridAdapter()


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GridViewViewModel::class.java)
    }

}