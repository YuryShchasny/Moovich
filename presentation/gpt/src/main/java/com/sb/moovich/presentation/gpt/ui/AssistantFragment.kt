package com.sb.moovich.presentation.gpt.ui

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.domain.entity.Message
import com.sb.moovich.presentation.gpt.adapter.MessagesAdapter
import com.sb.moovich.presentation.gpt.databinding.FragmentAssistantBinding
import com.sb.moovich.presentation.gpt.ui.model.AssistantFragmentEvent
import com.sb.moovich.presentation.gpt.ui.model.AssistantFragmentState
import com.sb.moovich.presentation.gpt.viewmodel.AssistantViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AssistantFragment : BaseFragment<FragmentAssistantBinding>() {

    private val viewModel: AssistantViewModel by viewModels()

    private val adapter = MessagesAdapter()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAssistantBinding {
        return FragmentAssistantBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.messagesRecyclerView.adapter = adapter
        setClickListeners()
        checkText(binding.messageEditText.text.toString())
        setEditText()
        observeState()
    }

    private fun setClickListeners() {
        binding.sendButton.setOnClickListener {
            viewModel.fetchEvent(AssistantFragmentEvent.SendMessage(binding.messageEditText.text.toString()))
            binding.messageEditText.clearFocus()
            hideKeyboard(binding.messageEditText)
            binding.messageEditText.text.clear()
        }
    }

    private fun setEditText() {
        binding.messageEditText.addTextChangedListener { text ->
           checkText(text.toString())
        }
    }

    private fun observeState() {
        collectWithLifecycle(viewModel.state) { state ->
            when (state) {
                is AssistantFragmentState.Content -> {
                    binding.sendButton.visibility = if(state.sending) View.GONE else View.VISIBLE
                    val helloMessage = Message(
                        role = Message.Role.GPT,
                        content = getStringCompat(com.sb.moovich.core.R.string.hello_message),
                        date = null
                    )
                    adapter.submit(listOf(helloMessage) + state.messages) {
                        binding.messagesRecyclerView.scrollToPosition(state.messages.size)
                    }
                }

                AssistantFragmentState.Loading -> adapter.submitShimmerList(10)
            }
        }
    }

    private fun checkText(text: String) {
        if (text.isEmpty()) {
            binding.sendButtonIcon.colorFilter =
                ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
            binding.sendButton.isClickable = false
        } else {
            binding.sendButtonIcon.colorFilter =
                ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(1f) })
            binding.sendButton.isClickable = true
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(requireContext(), InputMethodManager::class.java)
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
