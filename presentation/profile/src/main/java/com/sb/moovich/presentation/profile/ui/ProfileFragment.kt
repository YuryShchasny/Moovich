package com.sb.moovich.presentation.profile.ui

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sb.moovich.core.base.BaseDialogBuilder
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.presentation.profile.databinding.FragmentProfileBinding
import com.sb.moovich.presentation.profile.ui.model.ProfileFragmentEvent
import com.sb.moovich.presentation.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModels()
    private var dialog: Dialog? = null

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateCacheSize()
        setClickListeners()
        observeState()
    }

    private fun observeState() {
        collectWithLifecycle(viewModel.state) { state ->
            state?.let {
                binding.tokenTextView.text = state.token
                val cacheMB = state.cache.toFloat() / (1000 * 1000)
                binding.cacheSizeTextView.text =
                    getString(com.sb.moovich.core.R.string.mb, cacheMB)
                binding.messagesCountTextView.text =
                    getString(com.sb.moovich.core.R.string.messages_count, state.messages)
            }
        }
    }

    private fun setClickListeners() {
        binding.logOutCard.setOnClickListener {
            dialog?.cancel()
            dialog = BaseDialogBuilder(requireContext())
                .setTitle(getStringCompat(com.sb.moovich.core.R.string.are_you_sure))
                .setDescription(getStringCompat(com.sb.moovich.core.R.string.log_out_description))
                .setMainButton(getStringCompat(com.sb.moovich.core.R.string.log_out))
                .setSecondaryButton(getStringCompat(com.sb.moovich.core.R.string.cancel))
                .setMainButtonListener { viewModel.fetchEvent(ProfileFragmentEvent.Logout) }
                .build()
            dialog?.show()
        }
        binding.manageButton.setOnClickListener { openTelegramBot() }
        binding.aboutDeveloperCard.setOnClickListener { openDeveloper() }
        binding.clearHistoryCard.setOnClickListener {
            dialog?.cancel()
            dialog = BaseDialogBuilder(requireContext())
                .setTitle(getStringCompat(com.sb.moovich.core.R.string.are_you_sure))
                .setDescription(getStringCompat(com.sb.moovich.core.R.string.clear_history_description))
                .setMainButton(getStringCompat(com.sb.moovich.core.R.string.clear))
                .setSecondaryButton(getStringCompat(com.sb.moovich.core.R.string.cancel))
                .setMainButtonListener { viewModel.fetchEvent(ProfileFragmentEvent.ClearAssistantHistory) }
                .build()
            dialog?.show()
        }
        binding.clearCache.setOnClickListener { viewModel.fetchEvent(ProfileFragmentEvent.ClearCache) }
    }

    private fun openTelegramBot() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=kinopoiskdev_bot"))
        startActivity(intent)
    }

    private fun openDeveloper() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/YuryShchasny"))
        startActivity(intent)
    }
}
