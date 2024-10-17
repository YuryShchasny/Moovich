package com.sb.moovich.presentation.authorization.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.extensions.showMessage
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.presentation.authorization.databinding.FragmentAuthorizationBinding
import com.sb.moovich.presentation.authorization.viewmodel.AuthorizationViewModel
import dagger.hilt.android.AndroidEntryPoint
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
        setEditText()
        setClickListeners()
        ViewCompat.setOnApplyWindowInsetsListener(binding.descriptionTextView) { v, insets ->
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
            binding.descriptionTextView.maxLines = if (ime.bottom > 0) 2 else Integer.MAX_VALUE
            insets
        }
    }

    private fun setEditText() {
        binding.tokenEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used in this case
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used in this case
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val upperCaseText = it.toString().uppercase()
                    val resultText = upperCaseText.replace(Regex("[^a-zA-Z0-9\\s-]"), "")
                    if (resultText != it.toString()) {
                        it.replace(0, it.length, resultText)
                    }
                }
            }
        })
    }

    private fun setClickListeners() {
        binding.loginButton.setOnClickListener {
            viewModel.login(binding.tokenEditText.text.toString().uppercase())
        }
        binding.botLink.setOnClickListener {
            openTelegramBot()
        }
        binding.apiLink.setOnClickListener {
            openApiDocumentation()
        }
        binding.kinopoiskLink.setOnClickListener {
            openKinopoisk()
        }
        binding.developerLink.setOnClickListener {
            openDeveloper()
        }
    }

    private fun setObservable() {
        collectWithLifecycle(viewModel.state) { state ->
            if (state.isAuthorized) navigation.navigateToHome()
        }
        collectWithLifecycle(viewModel.error, Lifecycle.State.CREATED) {
            it.showMessage(binding.root)
        }
    }

    private fun openTelegramBot() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=kinopoiskdev_bot"))
        startActivity(intent)
    }

    private fun openApiDocumentation() {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://api.kinopoisk.dev/documentation"))
        startActivity(intent)
    }

    private fun openKinopoisk() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kinopoisk.ru"))
        startActivity(intent)
    }

    private fun openDeveloper() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/YuryShchasny"))
        startActivity(intent)
    }
}