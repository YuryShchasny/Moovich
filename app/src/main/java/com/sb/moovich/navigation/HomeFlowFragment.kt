package com.sb.moovich.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sb.moovich.R
import com.sb.moovich.databinding.HomeFlowFragmentBinding

class HomeFlowFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = HomeFlowFragmentBinding.inflate(inflater, container, false)
        val host = childFragmentManager.findFragmentById(R.id.homeFlowFragmentContainer) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(host.navController)
        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNavigationView) { v, insets ->
            v.setPadding(0, 0, 0, 0)
            insets
        }
        return binding.root
    }
}