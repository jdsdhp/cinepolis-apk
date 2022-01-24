package com.jdsdhp.cinepoliapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import com.jdsdhp.cinepoliapp.R
import com.jdsdhp.cinepoliapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handle the splash screen transition.
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

    }

    override fun onBackPressed() {
        val currentDestination = findNavController(R.id.nav_host).currentDestination
        if (currentDestination?.id == R.id.main_fragment) {
            finish()
            return
        }
        super.onBackPressed()
    }

}