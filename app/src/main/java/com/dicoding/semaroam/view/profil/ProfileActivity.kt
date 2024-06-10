package com.dicoding.semaroam.view.profil

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.semaroam.R
import com.dicoding.semaroam.view.fragment.VersionFragment

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        val fragmentManager = supportFragmentManager
        val versionFragment = VersionFragment()
        val fragment = fragmentManager.findFragmentByTag(VersionFragment::class.java.simpleName)
        if (fragment !is VersionFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + VersionFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.frame_container, versionFragment, VersionFragment::class.java.simpleName)
                .commit()
        }
    }
}