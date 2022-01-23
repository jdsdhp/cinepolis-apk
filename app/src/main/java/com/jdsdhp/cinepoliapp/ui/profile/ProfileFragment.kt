package com.jdsdhp.cinepoliapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jdsdhp.cinepoliapp.R
import com.jdsdhp.cinepoliapp.data.store.model.Profile
import com.jdsdhp.cinepoliapp.databinding.FragmentProfileBinding
import com.maxkeppeler.sheets.info.InfoSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        initUI()
        subscribeUI()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeUI() {
        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when {
                    uiState.isLoading -> binding.swipeLayout.isRefreshing = true
                    !uiState.errorMessage.isNullOrBlank() ->
                        binding.swipeLayout.isRefreshing = false
                    uiState.isLoadSuccess -> binding.swipeLayout.isRefreshing = false
                }
            }
        }
        viewModel.profile.observe(viewLifecycleOwner) {
            it?.let { it1 -> setData(it1) } ?: run {
                InfoSheet().show(requireActivity()) {
                    drawable(R.drawable.ic_round_error_24)
                    drawableColor(R.color.colorError)
                    cancelableOutside(false)
                    title(R.string.error)
                    content(R.string.no_profile_found_error)
                    displayNegativeButton(false)
                    onPositive(R.string.accept)
                }
            }
        }
    }

    private fun initUI() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    private fun setData(profile: Profile) {
        binding.run {
            inputName.setText(profile.name)
            inputLastName.setText(profile.lastName)
            inputEmail.setText(profile.email)
            inputCard.setText(profile.cardNumber)
        }
    }

}