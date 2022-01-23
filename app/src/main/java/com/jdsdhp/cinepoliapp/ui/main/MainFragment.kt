package com.jdsdhp.cinepoliapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.jdsdhp.cinepoliapp.R
import com.jdsdhp.cinepoliapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    //private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        initUI()
        //subscribeUI()
        return binding.root
    }

    private fun initUI() {
        setupBottomNav()
    }

    private fun setupBottomNav() {
        getNavHostController()?.let { navController ->
            binding.bottomNavigation.run {
                setupWithNavController(navController)
                setOnItemSelectedListener { menuItem ->
                    if (menuItem.itemId != selectedItemId) {
                        navController.navigate(menuItem.itemId)
                        binding.toolbar.title = menuItem.title
                        true
                    }
                    else false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*private fun subscribeUI() {
        //TODO Code this.
    }*/

    private fun getNavHostController(): NavController? =
        childFragmentManager.findFragmentById(R.id.nav_host_main)?.findNavController()

}