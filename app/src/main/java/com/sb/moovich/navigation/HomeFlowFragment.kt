package com.sb.moovich.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.sb.moovich.R
import com.sb.moovich.core.views.MoovichBottomNavigationView
import com.sb.moovich.databinding.HomeFlowFragmentBinding

class HomeFlowFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = HomeFlowFragmentBinding.inflate(inflater, container, false)
        val host =
            childFragmentManager.findFragmentById(R.id.homeFlowFragmentContainer) as NavHostFragment
        val menu = listOf(
            MoovichBottomNavigationView.MenuItem(
                "assistant",
                ContextCompat.getDrawable(requireContext(), com.sb.moovich.core.R.drawable.ic_moovich_gpt)!!,
                getString(com.sb.moovich.core.R.string.assistant)
            ),
            MoovichBottomNavigationView.MenuItem(
                "search",
                ContextCompat.getDrawable(requireContext(), com.sb.moovich.core.R.drawable.ic_search)!!,
                getString(com.sb.moovich.core.R.string.search)
            ),
            MoovichBottomNavigationView.MenuItem(
                "home",
                ContextCompat.getDrawable(requireContext(), com.sb.moovich.core.R.drawable.ic_home)!!,
                getString(com.sb.moovich.core.R.string.title_home)
            ),
            MoovichBottomNavigationView.MenuItem(
                "watch_list",
                ContextCompat.getDrawable(requireContext(), com.sb.moovich.core.R.drawable.ic_watch_list)!!,
                getString(com.sb.moovich.core.R.string.title_watch_list)
            ),
        )
        binding.bottomNavigationView.setNavMenu(menu)
        binding.bottomNavigationView.setupWithNavController(host.navController)
        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNavigationView) { v, insets ->
            v.setPadding(0, 0, 0, 0)
            insets
        }
        return binding.root
    }
}