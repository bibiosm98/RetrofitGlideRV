package com.example.retrofitglide.ui.grid

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitglide.R
import com.example.retrofitglide.databinding.GridViewFragmentBinding
import com.example.retrofitglide.network.ComicApiFilter

class GridViewFragment : Fragment() {
    private lateinit var viewModel: GridViewViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(GridViewViewModel::class.java)

        val binding = GridViewFragmentBinding.inflate(inflater)

        binding.viewModel = viewModel
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

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GridViewViewModel::class.java)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.show_antman_menu -> ComicApiFilter.SHOW_ANT
                R.id.show_ironman_menu -> ComicApiFilter.SHOW_IRON
                R.id.show_spiderman_menu -> ComicApiFilter.SHOW_SPIDER
                else -> ComicApiFilter.SHOW_ALL
            }
        )
        return true
    }
}