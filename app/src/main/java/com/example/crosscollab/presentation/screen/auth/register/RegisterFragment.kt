package com.example.crosscollab.presentation.screen.auth.register

import dagger.hilt.android.AndroidEntryPoint

package com.example.crosscollab.presentation.screen.auth.register

import com.example.crosscollab.R
import com.example.crosscollab.databinding.FragmentRegisterBinding
import com.example.crosscollab.presentation.common.BaseFragment
import com.example.crosscollab.presentation.screen.auth.register.RegisterContract

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDepartmentDropdown()
        setupTermsCheckbox()
        setupListeners()
        observeState()
        observeEffects()
    }

    private fun setupDepartmentDropdown() {
        val departments = arrayOf(
            "Engineering",
            "Marketing",
            "Sales",
            "Human Resources",
            "Finance",
            "Operations",
            "Customer Support"
        )
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_dropdown_item_1line,
            departments
        )
        binding.actvDepartment.setAdapter(adapter)
    }

    private fun setupTermsCheckbox() {
        val text = "I agree to the Terms of Service and Privacy Policy"
        val spannableString = SpannableString(text)

        // Terms of Service clickable
        val termsClickable = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Navigate to Terms of Service
                showSnackbar("Terms of Service clicked")
            }
        }

        // Privacy Policy clickable
        val privacyClickable = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Navigate to Privacy Policy
                showSnackbar("Privacy Policy clicked")
            }
        }

        spannableString.setSpan(termsClickable, 15, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(privacyClickable, 38, 52, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.cbTerms.text = spannableString
        binding.cbTerms.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun setupListeners() {
        with(binding) {
            // Clear errors on text change
            etFirstName.addTextChangedListener {
                tilFirstName.error = null
            }

            etLastName.addTextChangedListener {
                tilLastName.error = null
            }

            etEmail.addTextChangedListener {
                tilEmail.error = null
            }

            etPassword.addTextChangedListener {
                tilPassword.error = null
            }

            etConfirmPassword.addTextChangedListener {
                tilConfirmPassword.error = null
            }

            // Send OTP Button
            btnSendOtp.setOnClickListener {
                val phone = etPhone.text.toString().trim()
                if (phone.isNotEmpty()) {
                    showSnackbar("OTP sent to $phone")
                } else {
                    tilPhone.error = "Phone number required"
                }
            }

            // Sign Up Button
            btnSignUp.setOnClickListener {
                val firstName = etFirstName.text.toString().trim()
                val lastName = etLastName.text.toString().trim()
                val fullName = "$firstName $lastName"
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                val confirmPassword = etConfirmPassword.text.toString().trim()
                val acceptedTerms = cbTerms.isChecked

                viewModel.handleIntent(
                    AuthIntent.Register(
                        fullName = fullName,
                        email = email,
                        password = password,
                        confirmPassword = confirmPassword,
                        acceptedTerms = acceptedTerms
                    )
                )
            }

            // Navigate to Sign In
            tvSignIn.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is AuthState.Idle -> {
                        showLoading(false)
                    }
                    is AuthState.Loading -> {
                        showLoading(true)
                    }
                    is AuthState.Success -> {
                        showLoading(false)
                    }
                    is AuthState.Error -> {
                        showLoading(false)
                    }
                }
            }
        }

        // Observe field errors
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fullNameError.collectLatest { error ->
                if (error != null) {
                    binding.tilFirstName.error = error
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.emailError.collectLatest { error ->
                binding.tilEmail.error = error
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.passwordError.collectLatest { error ->
                binding.tilPassword.error = error
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.confirmPasswordError.collectLatest { error ->
                binding.tilConfirmPassword.error = error
            }
        }
    }

    private fun observeEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effect.collectLatest { effect ->
                when (effect) {
                    is AuthEffect.NavigateToHome -> {
                        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                    }
                    is AuthEffect.ShowError -> {
                        showSnackbar(effect.message)
                    }
                    is AuthEffect.ShowSnackbar -> {
                        showSnackbar(effect.message)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            btnSignUp.isEnabled = !isLoading
            etFirstName.isEnabled = !isLoading
            etLastName.isEnabled = !isLoading
            etEmail.isEnabled = !isLoading
            etPhone.isEnabled = !isLoading
            etPassword.isEnabled = !isLoading
            etConfirmPassword.isEnabled = !isLoading
            actvDepartment.isEnabled = !isLoading
            btnSendOtp.isEnabled = !isLoading
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}