package com.alfrsms.challange5.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alfrsms.challange5.R
import com.alfrsms.challange5.databinding.FragmentLoginBinding
import com.alfrsms.challange5.di.UserServiceLocator
import com.alfrsms.challange5.ui.home.HomeActivity
import com.alfrsms.challange5.utils.viewModelFactory


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModelFactory {
        LoginViewModel(UserServiceLocator.provideUserRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener { checkLogin() }

        if (isUserLoggedIn()) {
            navigateToHome()
        }

        binding.tvRegisterHere.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun checkLogin() {
        if (validateInput()) {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.getIfUserExist(username)
            viewModel.getIfUserExistResult.observe(viewLifecycleOwner) { exist ->
                if (exist) {
                    viewModel.checkIsUserLoginValid(username, password)
                    viewModel.checkIsUserLoginValid.observe(viewLifecycleOwner) {
                        setSharedPreference(username)
                        checkUser(it)
                    }
                } else {
                    setLoginState("Username not found")
                }
            }
        }
    }

    private fun setSharedPreference(username: String) {
        viewModel.getUserByUsername(username)
        viewModel.userByUsernameResult.observe(viewLifecycleOwner) {
            viewModel.setUserId(it.userId)
        }
    }

/*    private fun checkIfUsernameExists(username: String, password: String): Boolean {
        return viewModel.getIfUserExist(username, password)
    }*/

    private fun checkUser(userLoggedIn: Boolean?) {

        if (validateInput()) {
            userLoggedIn?.let {
                if (userLoggedIn) {
                    navigateToHome()
                    setLoginState("Login Success")
                } else {
                    setLoginState("Wrong password")
                }
                viewModel.setIfUserLogin(userLoggedIn)
            }
        }
    }

    private fun setLoginState(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun isUserLoggedIn(): Boolean {
        return viewModel.checkIfUserLoggedIn()
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        if (username.isEmpty()) {
            isValid = false
            binding.etUsername.error = "Username must not be empty"
        }
        if (password.isEmpty()) {
            isValid = false
            Toast.makeText(requireContext(), "Password must not be empty", Toast.LENGTH_SHORT)
                .show()
        }
        return isValid
    }

    private fun navigateToHome() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

}