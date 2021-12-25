package com.example.kitoky

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.kitoky.databinding.ActivityMainBinding
import androidx.lifecycle.ViewModelProvider






class MainActivity : AppCompatActivity() {
    var arrVideoModel = ArrayList<VideoModel>()
    var videoAdapter: VideoAdapter? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var mViewModel: VideoViewModel
    lateinit var viewpager: ViewPager

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //val view = binding.root
        //setContentView(view)
        replaceFragment(MainFragment.newInstance(), true)



    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()

    }

    fun replaceFragment(fragment: Fragment, istransition: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (istransition) {
            fragmentTransaction.setCustomAnimations(
                R.anim.slide_out_right,
                R.anim.slide_in_left
            )
        }
        fragmentTransaction.replace(com.example.kitoky.R.id.frameLayout, fragment)
            .addToBackStack(fragment.javaClass.simpleName)
        fragmentTransaction.commit()

    }
}


