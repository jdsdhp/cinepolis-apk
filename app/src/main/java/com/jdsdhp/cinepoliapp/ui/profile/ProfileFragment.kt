package com.jdsdhp.cinepoliapp.ui.profile

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jdsdhp.cinepoliapp.R
import com.jdsdhp.cinepoliapp.data.store.model.Profile
import com.jdsdhp.cinepoliapp.databinding.FragmentProfileBinding
import com.jdsdhp.cinepoliapp.ui.main.MainFragment
import com.jdsdhp.cinepoliapp.ui.main.MainFragmentDirections
import com.jdsdhp.cinepoliapp.ui.util.setSafeOnClickListener
import com.maxkeppeler.sheets.info.InfoSheet
import com.maxkeppeler.sheets.input.InputSheet
import com.maxkeppeler.sheets.input.ValidationResult
import com.maxkeppeler.sheets.input.type.InputEditText
import com.maxkeppeler.sheets.lottie.LottieAnimation
import com.maxkeppeler.sheets.lottie.withCoverLottieAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var recoverySheet: InputSheet

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
        recoverySheet = InputSheet().build(requireActivity()) {
            withCoverLottieAnimation(LottieAnimation {
                setupAnimation { setAnimation(R.raw.transaction_anim) }
            })
            title(R.string.check_transactions)
            with(InputEditText {
                startIconDrawable(R.drawable.ic_round_payment_24)
                hint(R.string.card_number)
                maxLines(1)
                required()
                inputType(InputType.TYPE_CLASS_NUMBER)
            })
            with(InputEditText {
                startIconDrawable(R.drawable.ic_round_flag_24)
                hint(R.string.country_code)
                maxLines(1)
                required()
                inputType(InputType.TYPE_CLASS_TEXT)
                validationListener { value ->
                    ValidationResult(
                        valid = value.trim().length == 2,
                        errorText = getString(R.string.just_two_characters_error)
                    )
                } // Add custom validation logic
            })
            with(InputEditText {
                startIconDrawable(R.drawable.ic_round_lock_24)
                hint(R.string.pin)
                maxLines(1)
                required()
                inputType(InputType.TYPE_CLASS_TEXT)
            })
            onNegative(R.string.cancel)
            onPositive(positiveRes = R.string.check) { result ->
                val cardNumber = result.getString("0")!!
                val countryCode = result.getString("1")!!
                val pin = result.getString("2")!!

                val fragment = this@ProfileFragment.requireParentFragment().requireParentFragment()
                if (fragment is MainFragment)
                    fragment.findNavController().navigate(
                        MainFragmentDirections.mainFragmentToLoyaltyFragment(
                            cardNumber = cardNumber,
                            countryCode = countryCode.uppercase(),
                            pin = pin,
                        )
                    )
            }
        }

        binding.run {
            swipeLayout.setOnRefreshListener { viewModel.refreshData() }
            loyaltyBtn.setSafeOnClickListener { recoverySheet.show() }
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