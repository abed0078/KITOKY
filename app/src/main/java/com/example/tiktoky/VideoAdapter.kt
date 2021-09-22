package com.example.tiktoky

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tiktoky.databinding.ItemVideoBinding


 class VideoAdapter (arrVideo:ArrayList<VideoModel>): RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
     var arrVideoModel:ArrayList<VideoModel> = arrVideo
//private lateinit var Binding: ItemVideoBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
return VideoViewHolder(ItemVideoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
holder.setVideoData(arrVideoModel[position])
    }

    override fun getItemCount(): Int {
        return arrVideoModel.size
    }

   class VideoViewHolder(val Binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(Binding.root) {

        fun setVideoData(videoModel: VideoModel) {
            Binding.tvTitle.text = videoModel.videoTitle
            Binding.tvDesc.text = videoModel.videoDesc
            Binding.videoView.setVideoPath(videoModel.videoUrl)
            Binding.videoView.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
                override fun onPrepared(mp: MediaPlayer) {
                    Binding.progressBar3.visibility = View.GONE
                    mp.start()
                    val videoRatio = mp.videoWidth.toFloat() / mp.videoHeight.toFloat()
                    val screenRatio = Binding.videoView.width.toFloat() / Binding.videoView.height.toFloat()
                    val scale = videoRatio / screenRatio
                    if (scale > 1f) {
                        Binding.videoView.scaleX = scale
                    } else {
                        Binding.videoView.scaleY = (1f / scale)
                    }


                }
            })

            Binding.videoView.setOnCompletionListener {
                object : MediaPlayer.OnCompletionListener {
                    override fun onCompletion(mp: MediaPlayer) {
                        mp.start()

                    }
                }
            }


        }
    }
}