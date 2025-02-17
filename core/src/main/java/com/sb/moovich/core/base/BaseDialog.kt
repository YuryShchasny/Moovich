package com.sb.moovich.core.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.sb.moovich.core.databinding.DialogBaseBinding

class BaseDialog(
    context: Context,
    private val title: String?,
    private val description: String?,
    private val mainButton: String?,
    private val secondaryButton: String?,
    private val mainButtonListener: (() -> Unit)?,
    private val secondaryButtonListener: (() -> Unit)?,
    private val onDismissListener: (() -> Unit)?,
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DialogBaseBinding.inflate(layoutInflater)

        binding.root.setOnClickListener { dismiss() }

        if (title == null) binding.titleTextView.visibility = View.GONE
        else binding.titleTextView.text = title

        if (description == null) binding.descriptionTextView.visibility = View.GONE
        else binding.descriptionTextView.text = description

        if (mainButton == null) {
            binding.mainButton.visibility = View.GONE
        } else {
            binding.mainButton.text = mainButton
            binding.mainButton.setOnClickListener {
                mainButtonListener?.invoke()
                dismiss()
            }
        }
        if (secondaryButton == null) {
            binding.secondaryButton.visibility = View.GONE
        } else {
            binding.secondaryButton.text = secondaryButton
            binding.secondaryButton.setOnClickListener {
                secondaryButtonListener?.invoke()
                dismiss()
            }
        }
        setContentView(binding.root)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }

    override fun dismiss() {
        onDismissListener?.invoke()
        super.dismiss()
    }
}

class BaseDialogBuilder(private val context: Context) {
    private var title: String? = null
    private var description: String? = null
    private var mainButton: String? = null
    private var secondaryButton: String? = null
    private var mainButtonListener: (() -> Unit)? = null
    private var secondaryButtonListener: (() -> Unit)? = null
    private var onDismissListener: (() -> Unit)? = null

    fun setTitle(text: String): BaseDialogBuilder {
        if (text.isNotBlank()) title = text
        return this
    }

    fun setDescription(text: String): BaseDialogBuilder {
        if (text.isNotBlank()) description = text
        return this
    }

    fun setMainButton(text: String): BaseDialogBuilder {
        if (text.isNotBlank()) mainButton = text
        return this
    }

    fun setSecondaryButton(text: String): BaseDialogBuilder {
        if (text.isNotBlank()) secondaryButton = text
        return this
    }

    fun setMainButtonListener(listener: () -> Unit): BaseDialogBuilder {
        mainButton?.let {
            mainButtonListener = listener
        }
        return this
    }

    fun setSecondaryButtonListener(listener: () -> Unit): BaseDialogBuilder {
        secondaryButton?.let {
            secondaryButtonListener = listener
        }
        return this
    }

    fun setOnDismissListener(listener: () -> Unit): BaseDialogBuilder {
        onDismissListener = listener
        return this
    }

    fun build(): BaseDialog {
        return BaseDialog(
            context,
            title,
            description,
            mainButton,
            secondaryButton,
            mainButtonListener,
            secondaryButtonListener,
            onDismissListener,
        )
    }
}