package com.example.anukoolfinal

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.anukoolfinal.databinding.ActivityMainBinding
import com.example.anukoolfinal.receiver.AirplaneModeChangeReceiver
import com.example.anukoolfinal.service.AppLockService
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(),NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myReceiver: AirplaneModeChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setItemIconTintList(null);
        navController!!.addOnDestinationChangedListener(this)


        startService(Intent(this, AppLockService::class.java))
        //startService(Intent(this, AppLaunchDetectorService::class.java))
        checkOverlayPermission()

       /* myReceiver= AirplaneModeChangeReceiver()
        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(myReceiver, filter)*/

    }

    override fun onDestroy() {
        super.onDestroy()
      //  unregisterReceiver(myReceiver);

    }

    // method for starting the service
    fun startService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // check if the user has already granted
            // the Draw over other apps permission
            if (Settings.canDrawOverlays(this)) {
                // start the service based on the android version
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                   // startForegroundService(Intent(this, ForegroundService::class.java))
                } else {
                  //  startService(Intent(this, ForegroundService::class.java))
                }
            }
        } else {
           // startService(Intent(this, ForegroundService::class.java))
        }
    }

    fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                // send user to the device settings
                val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                startActivity(myIntent)
            }
        }
    }











    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.selectAppFragment -> hideBottomNav()
            else -> {
                showBottomNav()
            }
        }
    }
    private fun showBottomNav() {
        binding.navView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.navView.visibility = View.GONE

    }
}