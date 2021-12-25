package com.example.kitoky


import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kitoky.Entity.Data
import com.example.kitoky.databinding.RecycleritemBinding
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.recycleritem.view.*
import kotlin.reflect.KFunction1


class VideoAdapter(arrVideo: List<Data>, requireContext: Context, recyclerView: RecyclerView  ) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    var arrVideoModel: List<Data> = arrVideo



    // private val mProductList: ArrayList<Data> = ArrayList()
  // private lateinit var mViewModel: VideoViewModel


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            RecycleritemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))

    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.setVideoData(arrVideoModel[position])

    }

    override fun getItemCount(): Int {
        return arrVideoModel.size
    }

    fun getVideoAt(position: Int): Data {
        return arrVideoModel.get(position)
    }

    fun setData(arrNotesList: List<Data>) {
        arrVideoModel = arrNotesList as ArrayList<Data>
    }
     interface RecyclerViewClickListener {
        fun onClicked(v: View,position:Int)
    }
    class VideoAdapter(private val onDeleteCallback: (Data) -> Unit) {}
/*fun removeProduct(model: Data) {
   val position = mProductList.indexOf(model)
   mProductList.remove(model)
   notifyItemRemoved(position)
}*/
  /*fun removeAt(position: Int) {
     arrVideoModel.remove(position)
     notifyItemRemoved(position)
     notifyItemRangeChanged(position, arrVideoModel.size)
 }*/

class VideoViewHolder(val Binding: RecycleritemBinding) :
   RecyclerView.ViewHolder(Binding.root) {


   fun setVideoData(data: Data) {
       Binding.textView11.text = data.Title
      // Binding.tvDesc.text = videoModel.videoDesc
       Binding.videoView1.setVideoPath(data.video)
       Binding.videoView1.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
           override fun onPrepared(mp: MediaPlayer) {
               Binding.progressBar4.visibility = View.GONE
               mp.start()
               val videoRatio = mp.videoWidth.toFloat() / mp.videoHeight.toFloat()
               val screenRatio =
                   Binding.videoView1.width.toFloat() / Binding.videoView1.height.toFloat()
               val scale = videoRatio / screenRatio
               if (scale > 1f) {
                   Binding.videoView1.scaleX = scale
               } else {
                   Binding.videoView1.scaleY = (1f / scale)
               }


           }
       })


       Binding.videoView1.setOnCompletionListener {
           object : MediaPlayer.OnCompletionListener {
               override fun onCompletion(mp: MediaPlayer) {
                   mp.start()


               }
           }
       }


   }

}

class DiffCallback : DiffUtil.ItemCallback<Data>() {
   override fun areItemsTheSame(oldItem: Data, newItem: Data) =
       oldItem.id == newItem.id


   @SuppressLint("DiffUtilEquals")
   override fun areContentsTheSame(oldItem: Data, newItem: Data) =
       oldItem == newItem

}




}










