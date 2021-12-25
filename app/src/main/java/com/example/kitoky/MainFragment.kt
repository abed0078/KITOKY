package com.example.kitoky

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.kitoky.Database.videoDatabase
import com.example.kitoky.Entity.Data
import com.example.kitoky.databinding.FragmentMainBinding
import kotlinx.coroutines.launch
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.example.kitoky.Database.videoDatabase.Companion.getInstance
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class MainFragment : BaseFragment(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {
    private var snapHelper: SnapHelper = LinearSnapHelper()
    private lateinit var mViewModel: VideoViewModel
    var videoAdapter: VideoAdapter? = null
    private lateinit var _binding: FragmentMainBinding
    private val binding get() = _binding
    private var REQUEST_VIDEO_CODE = 456
    private var videoPath = ""
    private var READ_STORAGE_PERM = 123
    private val arrvideo: List<Data> = ArrayList()
    private val mProductList: ArrayList<Data> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this)[VideoViewModel::class.java]
        return _binding.getRoot()
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        launch {
            context?.let {
                // var savedVideo = videoDatabase.getInstance(it).videoDao().getAllVideos()


                _binding.recyclerView.apply {
                    _binding.recyclerView.layoutManager = LinearLayoutManager(context)

                    mViewModel.readAllData.observe(viewLifecycleOwner, Observer { response ->
                        binding.recyclerView.adapter =
                            VideoAdapter(response as ArrayList<Data>, requireContext(), this)
                        snapHelper.attachToRecyclerView(_binding.recyclerView)


                        // _binding.recyclerView.layoutManager = LinearLayoutManager(context)
                        //  val adapter = VideoAdapter(savedVideo)
                        // _binding.viewPager.adapter = adapter
                        // adapter=videoAdapter

                        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                            0,
                            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                        ) {

                            override fun onMove(
                                recyclerView: RecyclerView,
                                viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder
                            ): Boolean {
                                return false
                            }


                            override fun onSwiped(
                                viewHolder: RecyclerView.ViewHolder,
                                direction: Int
                            ) {
                                // var position =videoAdapter!!.arrVideoModel[viewHolder.adapterPosition]
                                when (direction) {
                                    ItemTouchHelper.LEFT -> {
                                        var deletedVideo = Data()
                                        deletedVideo.video = videoPath
                                        mViewModel.deleteVideo(deletedVideo)
                                        // adapter?.notifyItemRemoved(position)
                                    }
                                }
                                /*val task = videoAdapter!!.arrVideoModel[viewHolder.adapterPosition]
                               mViewModel.deleteVideo(task)*/
                                /*  videoAdapter?.let { it1 ->

                                       mViewModel.deleteVideo(
                                           it1.getVideoAt(viewHolder.adapterPosition))
                                     }*/
                                // Toast.makeText(context, "video Deleted", Toast.LENGTH_LONG).show()
                            }


                        }).attachToRecyclerView(_binding.recyclerView)

                    })


                }

                _binding.fab.setOnClickListener {
                    //  pickImageFromGallery()
                    readStorageTask()


                }
            }


        }

    }


    fun replaceFragment(fragment: Fragment, istransition: Boolean) {
        val fragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        if (istransition) {
            fragmentTransaction.setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_right

            )
        }
        fragmentTransaction.replace(com.example.kitoky.R.id.frameLayout, fragment)
            .addToBackStack(fragment.javaClass.simpleName)
        fragmentTransaction.commit()

    }

    private fun hasReadStoragePerm(): Boolean {
        return EasyPermissions.hasPermissions(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }


    private fun readStorageTask() {
        if (hasReadStoragePerm()) {
            pickImageFromGallery()
        } else {
            EasyPermissions.requestPermissions(
                requireActivity(),
                getString(R.string.storage_permission_text),
                READ_STORAGE_PERM,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )

        }

    }

    fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        intent.type = "video/*"
        startActivityForResult(intent, REQUEST_VIDEO_CODE)


    }

    @SuppressLint("Recycle")
    fun getPath(uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor? =
            context?.contentResolver?.query(uri!!, projection, null, null, null)
        return if (cursor != null) {
            val columnIndex: Int = cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(columnIndex)
        } else null
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_VIDEO_CODE -> {
                    // val videoUri: Uri = data?.data!!
                    val videoUri: Uri = data?.data!!
                    videoPath = getPath(videoUri)!!
                    Log.d("TAG", "$videoPath is the path that you need...")
                    saveUser()


                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun saveUser() {
        val db = Room.databaseBuilder(
            requireContext(),
            videoDatabase::class.java, "sleep_history_database"
        ).build()
        launch {
            var userVideo = Data()
            userVideo.video = videoPath
            mViewModel.insertVideo(userVideo)
            Toast.makeText(context, "data inserted", Toast.LENGTH_LONG).show()
            /* context?.let {
                 getInstance(it).videoDao().insertNotes(userVideo)
                 Toast.makeText(context, "data inserted", Toast.LENGTH_LONG).show()
             }*/

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            requireActivity()
        )
    }


    fun deleteItem() {
        launch {
            var deleteVideo = Data()
            deleteVideo.video = videoPath
            context.let {
                if (it != null) {
                    getInstance(it).videoDao().deleteVideos(deleteVideo)
                }
                Toast.makeText(context, "video Deleted", Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(requireActivity(), perms)) {
            AppSettingsDialog.Builder(requireActivity()).build().show()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        TODO("Not yet implemented")
    }

    override fun onRationaleAccepted(requestCode: Int) {
        TODO("Not yet implemented")
    }

    override fun onRationaleDenied(requestCode: Int) {
        TODO("Not yet implemented")
    }

    private fun deleteWallet(data: Data) {
        mViewModel.deleteVideo(data)
    }


}































