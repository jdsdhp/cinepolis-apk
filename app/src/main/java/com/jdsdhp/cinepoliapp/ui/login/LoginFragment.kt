package com.jdsdhp.cinepoliapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.razir.progressbutton.DrawableButton
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.jdsdhp.cinepoliapp.R
import com.jdsdhp.cinepoliapp.databinding.FragmentLoginBinding
import com.jdsdhp.cinepoliapp.util.hideSoftKeyboard
import com.jdsdhp.cinepoliapp.util.isValidEmail
import com.maxkeppeler.sheets.info.InfoSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
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
                    uiState.isLoading -> binding.submitBtn.run {
                        showProgress {
                            isEnabled = false
                            buttonTextRes = R.string.loading
                            gravity = DrawableButton.GRAVITY_TEXT_END
                            textMarginRes = R.dimen.dim_margin_small
                            progressColor = currentTextColor
                        }
                    }
                    !uiState.errorMessage.isNullOrBlank() -> {
                        binding.submitBtn.run {
                            hideProgress(R.string.continue_text)
                            isEnabled = true
                        }
                        InfoSheet().show(requireActivity()) {
                            drawable(R.drawable.ic_round_error_24)
                            drawableColor(R.color.colorError)
                            cancelableOutside(false)
                            title(R.string.error)
                            content(uiState.errorMessage)
                            onPositive(R.string.accept)
                            onNegative(R.string.offline_mode) {
                                findNavController().navigate(LoginFragmentDirections.loginFragmentToMainFragment())
                            }
                        }
                    }
                    uiState.isUserLoggedIn -> {
                        binding.submitBtn.hideProgress(R.string.continue_text)
                        findNavController().navigate(LoginFragmentDirections.loginFragmentToMainFragment())
                    }
                }
            }
        }
    }

    private fun initUI() = binding.run {
        submitBtn.run {
            viewLifecycleOwner.bindProgressButton(this)
            setOnClickListener {
                isEnabled = false
                requireActivity().hideSoftKeyboard()
                if (isInputValid()) viewModel.sendLogin(
                    email = inputEmail.text.toString().trim(),
                    password = passwordInput.text.toString().trim(),
                ) else {
                    hideProgress(R.string.continue_text)
                    isEnabled = true
                }
            }
        }
        passwordInput.setOnEditorActionListener { _, _, _ ->
            submitBtn.callOnClick()
            true
        }
    }

    private fun isInputValid(): Boolean {
        var isValid = true
        val email = binding.inputEmail.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        if (email.isBlank()) {
            binding.inputLayoutEmail.error = getString(R.string.required_field)
            isValid = false
        } else if (!email.isValidEmail()) {
            binding.inputLayoutEmail.error = getString(R.string.invalid_email_error)
            isValid = false
        } else binding.inputLayoutEmail.error = null
        if (password.isNotBlank()) {
            if (password.length >= 4) binding.passwordInputLayout.error = null
            else {
                binding.passwordInputLayout.error = getString(R.string.password_length_error)
                isValid = false
            }
        } else {
            binding.passwordInputLayout.error = getString(R.string.required_field)
            isValid = false
        }
        return isValid
    }

}