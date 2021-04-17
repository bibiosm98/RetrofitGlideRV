package com.example.retrofitglide.ui.grid

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retrofitglide.R

class GridViewFragment : Fragment() {

    companion object {
        fun newInstance() = GridViewFragment()
    }

    private lateinit var viewModel: GridViewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.grid_view_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GridViewViewModel::class.java)
        // TODO: Use the ViewModel
    }

}