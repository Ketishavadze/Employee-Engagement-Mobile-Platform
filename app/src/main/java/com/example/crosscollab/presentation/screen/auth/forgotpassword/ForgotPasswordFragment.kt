package com.example.crosscollab.presentation.screen.auth.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.crosscollab.databinding.FragmentForgotPasswordBinding
import kotlinx.coroutines.launch

class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeState()
        observeEffects()
    }

    private fun setupViews() {
        // Email text change listener
        binding.etEmail.addTextChangedListener { text ->
            viewModel.onEvent(ForgotPasswordContract.Event.OnEmailChanged(text.toString()))
        }

        // Send reset link button
        binding.btnSendResetLink.setOnClickListener {
            viewModel.onEvent(ForgotPasswordContract.Event.OnSendResetLinkClicked)
        }

        // Back to sign in
        binding.llBackToSignIn.setOnClickListener {
            viewModel.onEvent(ForgotPasswordContract.Event.OnBackToSignInClicked)
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                updateUI(state)
            }
        }
    }

    private fun updateUI(state: ForgotPasswordContract.State) {
        // Update loading state
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.btnSendResetLink.isEnabled = !state.isLoading && state.isSendButtonEnabled
        binding.btnSendResetLink.alpha = if (state.isSendButtonEnabled && !state.isLoading) 1f else 0.5f

        // Update error message
        binding.tilEmail.error = state.emailError

        // Disable input when loading
        binding.etEmail.isEnabled = !state.isLoading
    }

    private fun observeEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effect.collect { effect ->
                handleEffect(effect)
            }
        }
    }

    private fun handleEffect(effect: ForgotPasswordContract.Effect) {
        when (effect) {
            is ForgotPasswordContract.Effect.NavigateToLogin -> {
                // Navigate back to login screen
                // findNavController().navigateUp()
                // OR
                // findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
                Toast.makeText(requireContext(), "Navigate to Login", Toast.LENGTH_SHORT).show()
                // For now, just close the fragment
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            is ForgotPasswordContract.Effect.ShowResetLinkSentSuccess -> {
                showSuccessDialog()
            }

            is ForgotPasswordContract.Effect.ShowError -> {
                Toast.makeText(requireContext(), effect.message, Toast.LENGTH_LONG).show()
            }

            is ForgotPasswordContract.Effect.ShowToast -> {
                Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Reset Link Sent")
            .setMessage("We've sent a password reset link to your email address. Please check your inbox and follow the instructions.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}