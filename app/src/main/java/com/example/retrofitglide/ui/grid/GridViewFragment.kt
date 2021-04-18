package com.example.retrofitglide.ui.grid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

        binding.imagesGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayComicDetails(it)
        })

        viewModel.navigateToSelectedComic.observe(viewLifecycleOwner, Observer {
            if( null != it) {
                this.findNavController().navigate(GridViewFragmentDirections.showDetail(it))
                viewModel.displayComicDetailsComplete()
            }
        })

//        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GridViewViewModel::class.java)
    }

}