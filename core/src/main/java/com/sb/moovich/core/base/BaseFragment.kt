package com.sb.moovich.core.base

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

abstract class BaseFragment<ViewBindingType : ViewDataBinding> : Fragment() {

    private var _binding: ViewBindingType? = null
    private val listOfAnimationView: MutableSet<View> = mutableSetOf()
    private val listOfAnimators: MutableSet<Animator> = mutableSetOf()

    val binding: ViewBindingType
        get() = requireNotNull(_binding)

    val bindingOrNull: ViewBindingType?
        get() = _binding

    abstract fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): ViewBindingType

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = setupViewBinding(inflater, container)
        return requireNotNull(_binding).root
    }

    override fun onDestroyView() {
        listOfAnimationView.forEach { it.animate()?.cancel() }
        listOfAnimators.forEach { it.cancel() }
        listOfAnimationView.clear()
        listOfAnimators.clear()
        super.onDestroyView()
        _binding = null
    }

    fun addAnimationView(vararg view: View) {
        listOfAnimationView.addAll(view)
    }

    fun addAnimator(vararg animator: Animator) {
        listOfAnimators.addAll(animator)
    }

    fun addOnBackPressedCallback(callback: () -> Unit) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            callback.invoke()
        }
    }

    fun getStringCompat(id: Int) = ContextCompat.getString(requireContext(), id)

    fun getColorCompat(id: Int) = ContextCompat.getColor(requireContext(), id)

    fun getDrawableCompat(id: Int) = ContextCompat.getDrawable(requireContext(), id)

    fun <T> collectWithLifecycle(
        flow: Flow<T>,
        state: Lifecycle.State = Lifecycle.State.STARTED,
        collector: FlowCollector<T>
    ) {
        lifecycleScope.launch {
            flow.flowWithLifecycle(viewLifecycleOwner.lifecycle, state)
                .collect(collector)
        }
    }
}
