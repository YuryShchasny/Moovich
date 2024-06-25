package com.sb.moovich.core.base

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

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

    fun addAnimationView(vararg view:View) {
        listOfAnimationView.addAll(view)
    }
    fun addAnimator(vararg animator: Animator) {
        listOfAnimators.addAll(animator)
    }

}