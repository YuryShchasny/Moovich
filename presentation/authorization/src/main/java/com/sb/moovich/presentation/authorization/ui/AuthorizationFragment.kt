package com.sb.moovich.presentation.authorization.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.presentation.authorization.databinding.FragmentAuthorizationBinding
import com.sb.moovich.presentation.authorization.viewmodel.AuthorizationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding>() {
    @Inject
    lateinit var navigation: INavigation
    private val viewModel: AuthorizationViewModel by viewModels()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAuthorizationBinding {
        return FragmentAuthorizationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservable()
        binding.loginButton.setOnClickListener {
            viewModel.login(binding.tokenEditText.text.toString())
        }
    }

    private fun setObservable() {
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    if(state.isAuthorized) navigation.navigateToHome()
                }
        }
    }
}