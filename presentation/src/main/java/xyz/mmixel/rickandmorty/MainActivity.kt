package xyz.mmixel.rickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import xyz.mmixel.rickandmorty.databinding.ActivityMainBinding
import xyz.mmixel.rickandmorty.ui.viewPager.ViewPagerListener

class MainActivity : AppCompatActivity(), ViewPagerListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)
        setContentView(binding.root)
    }

    override fun onResume() {
        setupActionBarWithNavController(findNavController(R.id.navHostFragment))
        super.onResume()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun pageChanged(stringId: Int) {
        supportActionBar?.title = resources.getString(stringId)
    }
}