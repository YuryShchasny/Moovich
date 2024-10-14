package com.sb.moovich.presentation.gpt.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.base.MySpeechRecognize
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

    private val permissionRegistry =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) startRecord()
        }
    private val speechRecognizer by lazy {
        MySpeechRecognize(requireContext())
    }

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
        setRecognizerListeners()
    }

    private fun setClickListeners() {
        binding.sendButton.setOnClickListener {
            viewModel.fetchEvent(AssistantFragmentEvent.SendMessage(binding.messageEditText.text.toString()))
            binding.messageEditText.clearFocus()
            hideKeyboard(binding.messageEditText)
            binding.messageEditText.text.clear()
        }
        binding.microphoneButton.setOnClickListener {
            if (checkMicroPermission()) {
                startRecord()
            } else {
                permissionRegistry.launch(Manifest.permission.RECORD_AUDIO)
            }
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
                    binding.sendButton.visibility = if (state.sending) View.GONE else View.VISIBLE
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

    private fun setRecognizerListeners() {
        speechRecognizer.setOnRmsChangedListener { rms ->
            bindingOrNull?.microphoneView?.rms = rms
        }
        speechRecognizer.setOnResultsListener { result ->
            bindingOrNull?.let { binding ->
                if (result.isNotEmpty()) {
                    binding.messageEditText.setText(result)
                    binding.messageEditText.setSelection(result.length)
                    viewModel.fetchEvent(AssistantFragmentEvent.SendMessage(result))
                    binding.messageEditText.text.clear()
                }
            }
        }
        speechRecognizer.setOnPartitionResultsListener { part ->
            bindingOrNull?.let { binding ->
                if (part.isNotEmpty()) {
                    binding.messageEditText.setText(part)
                    binding.messageEditText.setSelection(part.length)
                }
            }
        }
        speechRecognizer.setOnEndOfSpeechListener {
            bindingOrNull?.microphoneView?.visibility = View.GONE
        }
    }

    private fun startRecord() {
        binding.microphoneView.visibility = View.VISIBLE
        speechRecognizer.startRecord()
    }

    private fun checkMicroPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }
}
