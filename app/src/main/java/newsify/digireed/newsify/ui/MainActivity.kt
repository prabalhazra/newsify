package com.github.prabalhazra.newsify.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.github.prabalhazra.newsify.R
import com.github.prabalhazra.newsify.databinding.ActivityMainBinding
import com.github.prabalhazra.newsify.repository.NewsRepository
import com.github.prabalhazra.newsify.viewModel.NewsViewModel
import com.github.prabalhazra.newsify.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var _viewModel: NewsViewModel
    val viewModel get() = _viewModel

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        //viewModel
        val newsRepository = NewsRepository()
        val viewModelFactory = ViewModelFactory(application, newsRepository)
        _viewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeActivityFragment,
                R.id.searchActivityFragment,
                R.id.categoryActivityFragment
            )
        )
        //bottom nav
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment

        navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        setupActionBarWithNavController(navController, appBarConfiguration)

    }

        override fun onSupportNavigateUp(): Boolean {
            return navController.navigateUp() || super.onSupportNavigateUp()
        }
    }