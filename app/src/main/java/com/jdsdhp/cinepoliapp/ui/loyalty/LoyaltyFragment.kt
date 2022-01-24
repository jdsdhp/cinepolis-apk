package com.jdsdhp.cinepoliapp.ui.loyalty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jdsdhp.cinepoliapp.R
import com.jdsdhp.cinepoliapp.databinding.FragmentLoyaltyBinding
import com.jdsdhp.cinepoliapp.ui.util.RecyclerDecoration
import com.jdsdhp.cinepoliapp.ui.util.getDimension
import com.jdsdhp.cinepoliapp.ui.util.gone
import com.jdsdhp.cinepoliapp.ui.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoyaltyFragment : Fragment() {

    private val viewModel: LoyaltyViewModel by viewModels()
    private var _binding: FragmentLoyaltyBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoyaltyBinding.inflate(inflater, container, false)
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
                    uiState.isLoading -> binding.run {
                        recyclerView.gone()
                        infoView.gone()
                        cardView.gone()
                        loadingIndicator.visible()
                    }
                    !uiState.errorMessage.isNullOrBlank() -> binding.run {
                        loadingIndicator.gone()
                        swipeLayout.isRefreshing = false
                        recyclerView.gone()
                        infoText.text = uiState.errorMessage
                        infoIcon.setImageResource(R.drawable.ic_notification_alert)
                        infoView.visible()
                    }
                    uiState.isLoadSuccess -> binding.run {
                        swipeLayout.isRefreshing = false
                        loadingIndicator.gone()
                        cardView.visible()
                        recyclerView.visible()
                    }
                }
            }
        }
    }

    private fun initUI() {
        binding.run {
            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
            swipeLayout.setOnRefreshListener { viewModel.fetchLoyalty() }
            recyclerView.run {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = LinearLayoutManager.VERTICAL
                }
                addItemDecoration(RecyclerDecoration(requireContext().getDimension(R.dimen.dim_margin_small)))
                adapter = LoyaltyAdapter()
            }
        }
        viewModel.loyalty.observe(viewLifecycleOwner) {
            setCardData(it.name, it.email)
            if (!it.transactions.isNullOrEmpty()) {
                binding.run {
                    recyclerView.run {
                        (adapter as LoyaltyAdapter).submitList(it.transactions)
                        infoView.gone()
                        visible()
                    }
                }
            } else binding.recyclerView.gone()

        }
    }

    private fun setCardData(name: String, email: String) {
        binding.run {
            nameView.text = name
            emailView.text = email
        }
    }

}