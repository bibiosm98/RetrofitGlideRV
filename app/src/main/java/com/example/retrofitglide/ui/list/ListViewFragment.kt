package com.example.retrofitglide.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.retrofitglide.databinding.ListViewFragmentBinding
import com.example.retrofitglide.ui.grid.GridViewFragmentDirections
import com.example.retrofitglide.ui.grid.PhotoGridAdapter

class ListViewFragment : Fragment() {
    private lateinit var viewModel: ListViewViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(ListViewViewModel::class.java)

        val binding = ListViewFragmentBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.imagesList.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayComicDetails(it)
        })

        viewModel.navigateToSelectedComic.observe(viewLifecycleOwner, Observer {
            if( null != it) {
                this.findNavController().navigate(ListViewFragmentDirections.actionListViewFragmentToDetailFragment(it))
                viewModel.displayComicDetailsComplete()
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewViewModel::class.java)
    }

}