package com.example.tiktoky

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiktoky.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var arrVideoModel = ArrayList<VideoModel>()
    var videoAdapter: VideoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        arrVideoModel.add(
            VideoModel(
                "https://assets.mixkit.co/videos/preview/mixkit-tree-with-yellow-flowers-1173-large.mp4",
                "Tree with flowers",
                "The branches of a tree wave in the breeze, with pointy leaves "

            )
        )
        arrVideoModel.add(
            VideoModel(
                "https://assets.mixkit.co/videos/preview/mixkit-man-under-multicolored-lights-1237-large.mp4",
                "multicolored lights",
                "A man with a small beard and mustache wearing a white sweater, sunglasses, and a backwards black baseball cap turns his head in different directions under changing colored lights."
            )
        )
        arrVideoModel.add(
            VideoModel(
                "https://assets.mixkit.co/videos/preview/mixkit-man-holding-neon-light-1238-large.mp4",
                "holding neon light",
                "Bald man with a short beard wearing a large jean jacket holds a long tubular neon light thatch"
            )
        )
        arrVideoModel.add(
            VideoModel("https://assets.mixkit.co/videos/preview/mixkit-sun-over-hills-1183-large.mp4",
                "Sun over hills",
                "The sun sets or rises over hills, a body of water beneath them."

            )
        )



        videoAdapter = VideoAdapter(arrVideoModel)
        binding.viewPager.adapter = videoAdapter


    }
}